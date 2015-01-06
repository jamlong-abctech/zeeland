package com.abctech.zeeland.controller;

import com.abctech.zeeland.enumeration.AttributeGroup;
import com.abctech.zeeland.exception.ZeelandException;
import com.abctech.zeeland.form.SearchAd;
import com.abctech.zeeland.form.data.BookingOrderDetail;
import com.abctech.zeeland.form.data.BookingOrderItems;
import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.form.util.CategoryBuilder;
import com.abctech.zeeland.form.util.PageCalculator;
import com.abctech.zeeland.form.util.PageInformation;
import com.abctech.zeeland.form.util.ZeelandProperties;
import com.abctech.zeeland.form.validation.AdBatchDeleteValidation;
import com.abctech.zeeland.form.validation.SaveAdValidation;
import com.abctech.zeeland.form.validation.SearchAdValidation;
import com.abctech.zeeland.parser.AttributeValueParser;
import com.abctech.zeeland.service.AttributeObject;
import com.abctech.zeeland.service.AttributesGenerator;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.transform.adobject.ExtendedAdObjectTransformer;
import com.abctech.zeeland.wsObject.update.AdUpdate;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.enums.AdObjectPublishingStatus;
import no.zett.model.enums.CurrencyType;
import no.zett.model.enums.ObjectDataTableType;
import no.zett.model.enums.ObjectMediaType;
import no.zett.model.enums.ObjectStatus;
import no.zett.model.enums.OrderItemType;
import no.zett.service.facade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class AdController {

    private final static Logger log = LoggerFactory.getLogger(AdController.class);

    private final static String ADID = "adId";
    private final static String ZETTADOBJECT = "zettAdObject";
    private final static String TRANSITIONURL = "transitionURL";
    private final static String ZSERVICESURL = "zservicesurl";
    private final static String CATEGORYMAP = "categoryMap";
    private final static String REDIRECTSHOWAD = "redirect:showad.html?adId=";
    private final static String SHOWAD = "showad";
    @Autowired
    private AdUpdate adUpdate;

    @Autowired
    private ZeelandController zeelandController;

    @Autowired
    private CategoryBuilder categoryBuilder;

    @Autowired
    @Qualifier(value = "searchAdValidation")
    private SearchAdValidation searchAdValidation;

    @Autowired
    @Qualifier(value = "saveAdValidation")
    private SaveAdValidation saveAdValidation;

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private AdBatchDeleteValidation adBatchDeleteValidation;

    @Autowired
    private AdService adServicePortType;

    @Autowired
    private UserService userServicePortType;

    @Autowired
    private BookingAdminService bookingAdminService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FraudLogService fraudLogService;

    @Autowired
    private SearchItemService searchItemService;

    @Autowired
    private ZeelandProperties zeelandProperties;

    @Autowired
    private PrintAdService printAdService;

    @Autowired
    private AttributesGenerator attributesGenerator;

    @Autowired
    private AttributeValueParser parser;

    private final static String POST_METHOD = "POST";
    private static final String EXTENDED_AD_OBJECT = "extendedAdObject";
    private static final String ERROR_LIST = "errorList";

    @RequestMapping(value = "searchad.html")
    public String searchAd(Model model,
                           @ModelAttribute("searchAd") SearchAd searchAd,
                           BindingResult result, HttpServletRequest request, @RequestParam(value = "printAdId", required = false) String printAdId) throws ServiceException_Exception, IOException {
        log.debug("Start Searching ");
        model.addAttribute("keyword", "");
        searchAdValidation.validate(searchAd, result);
        model.addAttribute("zeelandUrl", "showad.html?adId=");
        model.addAttribute("zettUrl", zeelandProperties.getZettLink());
        webserviceAuthentication.authentication(adServicePortType);
        if (!result.hasErrors()) { // No error
            if (searchAd.getAdId() != null && !searchAd.getAdId().isEmpty()) { // Input Ad Id
                //response.sendRedirect("showad.html?adId=" + searchAd.getAdId()); // Redirect to showad.html
                return "forward:/showad.html";
            } else if (searchAd.getExtRef() != null && !searchAd.getExtRef().isEmpty()) {
                String extRef = searchAd.getExtRef();
                AdObjectResponse adObjectResponse = adServicePortType.loadObjectsByExternalReferenceValue(searchAd.getExtRef());
                return generateAdViewResultExRef(model, adObjectResponse, extRef);
            } else if (searchAd.getClientEmail() != null && !searchAd.getClientEmail().isEmpty()) {
                String email = searchAd.getClientEmail();
                AdObjectResponse adObjectResponse = adServicePortType.loadObjectsByCustomerEmail(searchAd.getClientEmail());
                return generateAdViewResultEmail(model, adObjectResponse, email);
            } else if (searchAd.getSearchKeyword() != null && !searchAd.getSearchKeyword().isEmpty()) {
                log.debug("search with keyword = " + searchAd.getSearchKeyword());
                String keyword = searchAd.getSearchKeyword();
                String status = String.valueOf(searchAd.getStatusValue());
                SearchItemResponse searchItemResponse = searchItemService.adObjectSearch(keyword, status, 1);
                return createAdSearchResultPageByFilter(model, searchItemResponse, searchAd.getSearchKeyword(), String.valueOf(searchAd.getStatusValue()), "1");
            } else if ((searchAd.getPrintAdId() != null && !searchAd.getPrintAdId().isEmpty()) || (request.getMethod().equals("GET") && printAdId != null)) {
                Integer id = null;
                if (printAdId != null) {
                    try {
                        id = Integer.parseInt(printAdId);
                    } catch (NumberFormatException e) {

                        model.addAttribute("errormsg", "validation.numeric");
                    }


                } else {
                    id = Integer.parseInt(searchAd.getPrintAdId().trim());

                }

                return retrievePrintAdObject(model, searchAd, printAdId, id);
            } else if (searchAd.getOrderId() != null && !searchAd.getOrderId().isEmpty()) {
                log.debug("search by Order Id : " + searchAd.getOrderId());
                Integer orderId;
                orderId = Integer.parseInt(searchAd.getOrderId());
                AdBookingOrderResponse adBookingOrderResponse = bookingAdminService.getAdBookingOrderByOrderId(orderId);
                ZettAdBookingOrderObject zettAdBookingOrderObject = adBookingOrderResponse.getZettAdBookingOrderObject();
                if (zettAdBookingOrderObject != null) {
                    AdBookingResponse adBookingResponse = bookingAdminService.getAdBookingByBookingId(zettAdBookingOrderObject.getBookingId());
                    ZettAdBooking zettAdBooking = adBookingResponse.getZettAdBooking();
                    if (zettAdBooking != null) {
                        log.debug("AdObject ID  = " + zettAdBooking.getAdObjectId());
                        return REDIRECTSHOWAD + zettAdBooking.getAdObjectId();
                    }

                } else {
                    model.addAttribute("errorinput", orderId);
                    model.addAttribute("errormsg", "validation.ad.notfound.byorderid");
                }
            }

        }


        return "searchad";
    }

    private String retrievePrintAdObject(Model model, SearchAd searchAd, String printAdId, Integer id) throws ServiceException_Exception {
        if (searchAd != null && printAdId != null && id != null) {
            log.debug("search printAd with printAd = " + searchAd.getPrintAdId());
            PrintAdObjectResponse printAdObjectResponse = printAdService.loadPrintAdById(id);
            ZettPrintAdObject zettPrintAdObject = printAdObjectResponse.getZettPrintAdObject();
            if (zettPrintAdObject != null) {
                for (ZettObjectAttribute zettObjectAttribute : zettPrintAdObject.getAttributes()) {
                    if (zettObjectAttribute.getName().equals("printad_newspaperurl")) {
                        model.addAttribute("newapapaerurl", zettObjectAttribute.getValue());
                    }
                    if (zettObjectAttribute.getName().equals("printad_text")) {
                        model.addAttribute("adtext", zettObjectAttribute.getValue());
                    }
                    if (zettObjectAttribute.getName().equals("printad_activedates")) {
                        model.addAttribute("activedates", zettObjectAttribute.getValue());
                    }
                    if (zettObjectAttribute.getName().equals("printad_contactinfo")) {
                        model.addAttribute("contactno", zettObjectAttribute.getValue());
                    }
                    if (zettObjectAttribute.getName().equals("printad_extralinesprice")) {
                        model.addAttribute("price", zettObjectAttribute.getValue());
                    }
                }

                model.addAttribute("zettPrintAdObject", zettPrintAdObject);

                if (zettPrintAdObject.getModifiedTime() != null) {
                    model.addAttribute("zettPrintAdObjectModifiedTime",
                            zettPrintAdObject.getModifiedTime().toGregorianCalendar().getTimeInMillis());
                } else {
                    //If no such an modification time, then '0' should be fine
                    model.addAttribute("zettPrintAdObjectModifiedTime", "0");
                }

                model.addAttribute("imageVersion", zeelandProperties.getObscuraImageVersion());

                AdObjectResponse adObjectResponse = adServicePortType.loadObjectByPrintAdId(Integer.parseInt(searchAd.getPrintAdId().trim()));
                ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();

                if (zettAdObject != null) {
                    model.addAttribute("adId", zettAdObject.getObjectId());
                }
                ////TODO REVIEW 1/17/13, following sentence can produce NPE, why the "if (zettAdObject != null)" is not here ?
                List<BookingOrderDetail> bookingOrderDetails = retrieveBookingOrderSearch(String.valueOf(zettAdObject.getObjectId()));
                Collections.sort(bookingOrderDetails);
                model.addAttribute("bookingOrderDetailSize", bookingOrderDetails.size());
                model.addAttribute("bookingOrderDetail", bookingOrderDetails);
                model.addAttribute("adurl" + "showad.html");
                return "showprintad";
            }
        }
        model.addAttribute("errorinput", printAdId);
        model.addAttribute("errormsg", "show.printad.noresultid");
        return "searchad";
    }

    @RequestMapping(value = "delad.html")
    public String gotoBatchDelete(Model model, @ModelAttribute("searchAd") SearchAd searchAd) {
        return "delad";
    }

    @RequestMapping(value = "showprintad.html")
    public String showPrintAd(Integer printAdId) throws ServiceException_Exception {
        return "showprintad";
    }

    @RequestMapping(value = "batchdeletead.html")
    public String batchDeleteAd(Model model, @ModelAttribute("searchAd") SearchAd searchAd,
                                BindingResult result) throws ServiceException_Exception, IOException {

        adBatchDeleteValidation.validate(searchAd, result);

        if (!result.hasErrors()) {
            String adIds = searchAd.getAdIds();
            adIds.replaceAll("\\n", "\\s");
            String[] adIdArray = adIds.split("\\s+");

            webserviceAuthentication.authentication(adServicePortType);
            List<Integer> idsList = new ArrayList<Integer>();
            for (String idStr : adIdArray) {
                idsList.add(new Integer(idStr));
            }
            String action = searchAd.getBatchAction();
            AdObjectBatchResponse adObjectBatchResponse = null;
            if ("delete".equalsIgnoreCase(action)) {
                adObjectBatchResponse = adServicePortType.deleteAds(idsList);
                model.addAttribute("result", generateBatchResult(adObjectBatchResponse, action));
            }
            if ("sold".equalsIgnoreCase(action)) {
                adObjectBatchResponse = adServicePortType.soldAds(idsList);
                model.addAttribute("result", generateBatchResult(adObjectBatchResponse, action));
            } else {
                adObjectBatchResponse = adServicePortType.inactiveAds(idsList);
                model.addAttribute("result", generateBatchResult(adObjectBatchResponse, action));
            }
        }
        return "delad";
    }

    private String generateBatchResult(AdObjectBatchResponse adObjectBatchResponse, String action) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<pre>Batch " + action + " ad(s) result");
        resultBuilder.append("</br>" + action + ": ");
        if (adObjectBatchResponse.getUpdatedAdIds() == null) {
            resultBuilder.append("0");
        } else {
            resultBuilder.append(adObjectBatchResponse.getUpdatedAdIds().size());
        }
        resultBuilder.append(" ad(s)");
        resultBuilder.append("</br>ad not found: ");
        if (adObjectBatchResponse.getNotFoundAdIds() == null) {
            resultBuilder.append("0");
        } else {
            resultBuilder.append(adObjectBatchResponse.getNotFoundAdIds().size());
        }
        resultBuilder.append(" ad(s)");
        resultBuilder.append("</br>can not " + action + ":");
        if (adObjectBatchResponse.getNotUpdatedAdIds() == null) {
            resultBuilder.append("0");
        } else {
            resultBuilder.append(adObjectBatchResponse.getNotUpdatedAdIds().size());
        }
        resultBuilder.append(" ad(s)</pre>");

        return resultBuilder.toString();
    }

    private String generateAdViewResultExRef(Model model, AdObjectResponse adObjectResponse, String extRef) {
        if (adObjectResponse != null && adObjectResponse.getZettAdObjects() != null) {

            if (adObjectResponse.getZettAdObjects() != null && adObjectResponse.getZettAdObjects() != null && adObjectResponse.getZettAdObjects().size() != 0) {
                model.addAttribute("adObjectPublishingStatusValues", AdObjectPublishingStatus.values());
                //ZettAdObject[] arrayOfZettAdObject = adObjectResponse.getZettAdObjects();
                model.addAttribute("zettAdObjectList", adObjectResponse.getZettAdObjects());
                model.addAttribute("total", adObjectResponse.getZettAdObjects().size());

                return "showlistad";
            }
        }

        return zeelandController.getLinkBackSearchPageByExternalRef(model, extRef);

    }

    private String generateAdViewResultEmail(Model model, AdObjectResponse adObjectResponse, String email) {
        if (adObjectResponse != null && adObjectResponse.getZettAdObjects() != null) {
            if (adObjectResponse.getZettAdObjects() != null && adObjectResponse.getZettAdObjects() != null) {
                model.addAttribute("adObjectPublishingStatusValues", AdObjectPublishingStatus.values());
                //ZettAdObject[] arrayOfzettAdObject = adObjectResponse.getZettAdObjects();
                model.addAttribute("zettAdObjectList", adObjectResponse.getZettAdObjects());
                model.addAttribute("total", adObjectResponse.getZettAdObjects().size());
                return "showlistad";
            }
        }

        return zeelandController.getLinkBackSearchPageByEmail(model, email);

    }

    private String generateAdViewResultSearch(Model model, AdObjectResponse adObjectResponse, String keyword, String page) {
        if (adObjectResponse != null && adObjectResponse.getZettAdObjects() != null) {
            if (adObjectResponse.getZettAdObjects() != null && adObjectResponse.getZettAdObjects() != null) {
                model.addAttribute("adObjectPublishingStatusValues", AdObjectPublishingStatus.values());
                model.addAttribute("zettAdObjectList", adObjectResponse.getZettAdObjects());
                int total = (int) adObjectResponse.getSolrResultSize();
                model.addAttribute("total", adObjectResponse.getSolrResultSize());
                PageCalculator pageCalculator = new PageCalculator();
                PageInformation pageInformation = pageCalculator.calculate(page, total);
                model.addAttribute("currentPage", pageInformation.getCurrentPage());
                model.addAttribute("totalPage", pageInformation.getTotalPage());
                model.addAttribute("type", "search");
                model.addAttribute("keyword", keyword);
                SearchAd searchAd = new SearchAd();
                searchAd.setSearchKeyword(keyword);
                model.addAttribute("searchAd", searchAd);
                model.addAttribute("zettAdUrl", zeelandProperties.getZettUrl());
                return "showlistad";
            }
        }

        return zeelandController.getLinkBackSearchPageBySearch(model, keyword);
    }


    private String createAdSearchResultPageByFilter(Model model, SearchItemResponse searchItemResponse, String keyword, String status, String page) {
        if (searchItemResponse != null && searchItemResponse.getSolrResultSize() != 0) {

            model.addAttribute("adSearchItems", searchItemResponse.getZettSearchItems());
            int totalResult = (int) searchItemResponse.getSolrResultSize();
            log.debug("Total Result ::: " + totalResult);
            model.addAttribute("totalResult", totalResult);
            PageCalculator pageCalculator = new PageCalculator();
            PageInformation pageInformation = pageCalculator.calculate(page, totalResult);
            model.addAttribute("currentPage", pageInformation.getCurrentPage());
            model.addAttribute("totalPage", pageInformation.getTotalPage());
            model.addAttribute("type", "search");
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);
            model.addAttribute("zettAdUrl", zeelandProperties.getZettUrl());
            return "showlistadbyfilter";


        } else {
            log.debug("THe Search Not Found");
            String showstatus = status;
            if (showstatus.equals("-1")) {
                showstatus = "Vis alle";
                return zeelandController.getLinkBackSearchPageByFilterSearch(model, keyword, showstatus);

            } else {
                showstatus = ObjectStatus.fromInteger(Integer.parseInt(showstatus)).toTextValue();
                return zeelandController.getLinkBackSearchPageByFilterSearch(model, keyword, showstatus);


            }
        }

    }

    @RequestMapping(value = "searchAdByPageByFilter.html")
    public String searchAdByPageByFilter(Model model,
                                         @ModelAttribute("searchAd") SearchAd searchAd,
                                         BindingResult result,
                                         @RequestParam(value = "totalPage", required = false) Integer totalPage,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "status", required = false) String status,
                                         @RequestParam(value = "totalResult", required = false) String totalResult,
                                         @RequestParam(value = "page", required = false) Integer page) throws ServiceException_Exception {


        ModelAndView modelAndView = new ModelAndView();
        searchAd.setSearchKeyword(keyword);
        searchAd.setStatusValue(Integer.parseInt(status));
        SearchItemResponse searchItemResponse = searchItemService.adObjectSearch(keyword, status, page);

        modelAndView.addObject("adSearchItems", searchItemResponse.getZettSearchItems());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("totalResult", totalResult);
        modelAndView.addObject("currentPage", page);


        return createAdSearchResultPageByFilter(model, searchItemResponse, keyword, status, Integer.toString(page));


    }

    @RequestMapping(value = "createaddowner.html")
    public String createAdOwner(@RequestParam(value = "hdadId", required = false) String adId,
                                @RequestParam(value = "hdtitle", required = false) String title,
                                @RequestParam(value = "hduserEmail", required = false) String email) throws ServiceException_Exception {

        webserviceAuthentication.authentication(adServicePortType);
        log.debug("AdId >>>>" + adId);
        log.debug("AdTitle >>>>" + title);
        log.debug("Email >>>> " + email);
        String editUrl = "";
        if (email.isEmpty()) {
            return REDIRECTSHOWAD + adId + "&error=validation.emptyField.user.email";
        }

        UserResponse userResponse = userServicePortType.loadUserByEmail(email);
        if (userResponse.getZettUser() != null) {

            adServicePortType.associateObjectWithUser(adId, title, editUrl, email);
            return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
        } else {

            return REDIRECTSHOWAD + adId + "&error=showuser.message.warning.noresult.email";

        }

    }

    @RequestMapping(value = "createcompanyowner.html")
    public String createCompanyOwner(@RequestParam(value = "currentAdId", required = false) Integer adId,
                                     @RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "updateMessage", required = false) String updateMessage,
                                     @RequestParam(value = "additionalcompanyId", required = false) String additionalcompanyId,
                                     @RequestParam(value = "companyOwnerId", required = false) Integer companyId) throws ServiceException_Exception {

        boolean setCompany = false;
        boolean setAdditionalCompany = false;

        additionalcompanyId = additionalcompanyId.replaceAll(" ", "");

        if (companyId != null) {
            log.debug("Company Id   " + companyId);
            setCompany = true;
        }
        if (additionalcompanyId != null && !additionalcompanyId.isEmpty()) {
            setAdditionalCompany = true;
        }

        if ((setCompany) && (!setAdditionalCompany)) {
            String msg = assignCompanyOwner(adId, companyId);
            return REDIRECTSHOWAD + adId + "&updateMessage" + msg;

        } else if ((setAdditionalCompany) && (!setCompany)) {

            String msg = assignAdditionalCompany(additionalcompanyId, adId);
            return REDIRECTSHOWAD + adId + "&updateMessage" + msg;

        } else if (setCompany && setAdditionalCompany) {

            String msg = assignCompanyOwnerandAddtionalOwner(adId, companyId, additionalcompanyId);
            return REDIRECTSHOWAD + adId + "&updateMessage" + msg;

        } else if (!setAdditionalCompany) {

            String msg = assignAdditionalCompany(additionalcompanyId, adId);
            return REDIRECTSHOWAD + adId + "&updateMessage" + msg;

        } else {
            return REDIRECTSHOWAD + adId;
        }/*
                  if (!setAdditionalCompany) {
            String msg = assignAdditionalCompany(additionalcompanyId, adId);
            return REDIRECTSHOWAD + adId + "&updateMessage" + msg;
        }*/

    }


    private String assignAdditionalCompany(String companyIdList, Integer adId) throws ServiceException_Exception {
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
        Integer id;

        if (companyIdList == null || companyIdList.isEmpty()) {
            zettAdObject.getAdObjectAdditionalCompanies().removeAll(zettAdObject.getAdObjectAdditionalCompanies());
            adServicePortType.saveObject(zettAdObject);
            return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
        }

        if (!companyIdList.endsWith(",")) {
            StringBuffer additinalId = new StringBuffer();
            additinalId.append(companyIdList).append(",");
            companyIdList = additinalId.toString();
        }
        String[] additionalCompanyownerIdList = companyIdList.split(",");

        Set<Integer> inactiveCompany = new TreeSet<Integer>();
        Set<Integer> notFoundCompany = new TreeSet<Integer>();
        Set<Integer> activeComapny = new TreeSet<Integer>();

        for (String addtionalCompanyId : additionalCompanyownerIdList) {

            try {
                id = Integer.parseInt(addtionalCompanyId);
            } catch (NumberFormatException e) {
                return REDIRECTSHOWAD + adId + "&error=validation.companyid.wrong.format";
            }

            CompanyObjectResponse companyObjectResponse = companyService.loadCompany(id);
            if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 0) {
                inactiveCompany.add(id);

            } else if (companyObjectResponse.getZettCompanyObject() == null) {

                notFoundCompany.add(id);


            } else if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 1) {
                activeComapny.add(id);
            }
        }

        if (inactiveCompany.size() == 0 && notFoundCompany.size() == 0) {
            if (activeComapny.size() != 0) {
                zettAdObject.getAdObjectAdditionalCompanies().removeAll(zettAdObject.getAdObjectAdditionalCompanies());
                for (Integer activeId : activeComapny) {
                    ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany = new ZettAdObjectAdditionalCompany();
                    zettAdObjectAdditionalCompany.setObjectId(adId);
                    zettAdObjectAdditionalCompany.setCompanyId(activeId);
                    zettAdObject.getAdObjectAdditionalCompanies().add(zettAdObjectAdditionalCompany);
                }
                adServicePortType.saveObject(zettAdObject);
                return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
            } else {
                return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
            }


        }
        return REDIRECTSHOWAD + adId + "&error=validation.company.invalidandnotfound ";
    }

    private String assignCompanyOwner(Integer adId, Integer companyId) throws ServiceException_Exception {


        CompanyObjectResponse companyObjectResponse = companyService.loadCompany(companyId);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
        if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 1) {
            zettAdObject.setCompany(zettCompanyObject);
            zettAdObject.getAdObjectAdditionalCompanies().removeAll(zettAdObject.getAdObjectAdditionalCompanies());
            adServicePortType.saveObject(zettAdObject);

        } else if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 0) {

            return REDIRECTSHOWAD + adId + "&error=showcompany.message.warning.inactive  ";

        } else {

            return REDIRECTSHOWAD + adId + "&error=validation.company.notfound ";

        }


        return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated ";
    }


    private String assignCompanyOwnerandAddtionalOwner(Integer adId, Integer companyId, String companyIdList) throws ServiceException_Exception {


        CompanyObjectResponse companyObjectResponse = companyService.loadCompany(companyId);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
        List<Integer> inactiveCompany = new ArrayList<Integer>();
        List<Integer> notFoundCompany = new ArrayList<Integer>();
        List<Integer> activeComapny = new ArrayList<Integer>();
        List<Integer> existingAdditional = new ArrayList<Integer>();

        if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 1) {


            zettAdObject.setCompany(zettCompanyObject);
            Integer id;

            if (!companyIdList.endsWith(",")) {
                StringBuffer additinalId = new StringBuffer();
                additinalId.append(companyIdList).append(",");
                companyIdList = additinalId.toString();
            }
            String[] additionalCompanyownerIdList = companyIdList.split(",");


            for (String addtionalCompanyId : additionalCompanyownerIdList) {

                try {
                    id = Integer.parseInt(addtionalCompanyId);
                } catch (NumberFormatException e) {
                    return REDIRECTSHOWAD + adId + "&error=validation.companyid.wrong.format ";
                }

                CompanyObjectResponse companyObjectResponseforAdditional = companyService.loadCompany(id);
                if (companyObjectResponseforAdditional.getZettCompanyObject() != null && companyObjectResponseforAdditional.getZettCompanyObject().getStatus() == 0) {

                    inactiveCompany.add(id);


                } else if (companyObjectResponseforAdditional.getZettCompanyObject() == null) {
                    notFoundCompany.add(id);


                } else if (companyObjectResponseforAdditional.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 1) {
                    activeComapny.add(id);
                }


            }

            if (inactiveCompany.size() == 0 && notFoundCompany.size() == 0) {
                //Check the existing Additional Comapny ID . Remove the duplicatie if the user input the id which is already exist
                if (zettAdObject.getAdObjectAdditionalCompanies().size() != 0) {
                    for (ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany : zettAdObject.getAdObjectAdditionalCompanies()) {
                        existingAdditional.add(zettAdObjectAdditionalCompany.getCompanyId());
                    }

                    activeComapny.removeAll(existingAdditional);
                }


                if (activeComapny.size() != 0) {
                    for (Integer activeId : activeComapny) {
                        ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany = new ZettAdObjectAdditionalCompany();
                        zettAdObjectAdditionalCompany.setObjectId(adId);
                        zettAdObjectAdditionalCompany.setCompanyId(activeId);
                        zettAdObject.getAdObjectAdditionalCompanies().add(zettAdObjectAdditionalCompany);
                    }
                    adServicePortType.saveObject(zettAdObject);
                    return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
                } else if (activeComapny.size() == 0) {
                    adServicePortType.saveObject(zettAdObject);
                    return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";

                } else {
                    return REDIRECTSHOWAD + adId + "&error=validation.company.invalidandnotfound ";
                }


            } else {
                return REDIRECTSHOWAD + adId + "&error=validation.company.invalidandnotfound ";
            }

        } else if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject().getStatus() == 0) {
            return REDIRECTSHOWAD + adId + "&error=showcompany.message.warning.inactive  ";
        }


        return REDIRECTSHOWAD + adId + "&error=validation.company.notfound ";
    }


    @RequestMapping(value = "searchadresultbypage.html")
    public String searchAdResultByPage(Model model,
                                       @RequestParam(value = "keyword", required = false) String keyword,
                                       @RequestParam(value = "page", required = false) String pageString) throws ServiceException_Exception {

        webserviceAuthentication.authentication(adServicePortType);
        int page = 1;
        try {
            page = Integer.valueOf(pageString);
        } catch (NumberFormatException err) {
            log.warn("can not cast this value to integer = " + pageString + " : " + err.getMessage());
        }
        AdObjectResponse adObjectResponse = adServicePortType.searchObject(keyword, page);
        return generateAdViewResultSearch(model, adObjectResponse, keyword, Integer.toString(page));
    }


    @RequestMapping(value = "showad.html")
    public String showSingleAd(Model model,
                               @ModelAttribute(EXTENDED_AD_OBJECT) ExtendedAdObject startedExtendedAdObject,
                               @RequestParam(value = ADID, required = false) Integer adId,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "updateMessage", required = false) String updateMessage,
                               HttpServletRequest request) throws ServiceException_Exception, ParseException, IOException {
        Integer showAdId = 0;


        try {
            if (request.getMethod().equals(POST_METHOD)) {
                showAdId = Integer.valueOf(request.getParameter(ADID).trim());
            } else {// if (request.getMethod().equals("GET")) {
                if (adId != null) {
                    showAdId = adId;
                }
            }

            if (request.getParameter(ERROR_LIST) != null) {
                model.addAttribute(ERROR_LIST, request.getParameter(ERROR_LIST));
            }

            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(showAdId);

            if (adObjectResponse == null || adObjectResponse.getZettAdObject() == null) { // No search result

                return zeelandController.getLinkBackSearchPageByID(model, showAdId);
            }

            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();


            log.debug("There are  " + zettAdObject.getMedia().size() + "  images");

            String zettAdUrl = createZettUrl(zettAdObject.getCategory(), zettAdObject.getObjectId());
            ExtendedAdObject extendedAdObject = new ExtendedAdObjectTransformer().transform(zettAdObject);
            model.addAttribute(ZETTADOBJECT, zettAdObject);
            model.addAttribute(EXTENDED_AD_OBJECT, extendedAdObject);
            model.addAttribute(CATEGORYMAP, categoryBuilder.getCategoryMap());
            model.addAttribute(TRANSITIONURL, zeelandProperties.getTransitionURL());
            model.addAttribute(ZSERVICESURL, zeelandProperties.getZservicesurlpaht());
            model.addAttribute("zettAdUrl", zettAdUrl);

            if (extendedAdObject.getAdObjectCategory() != null && extendedAdObject.getAdObjectCategory().getFullname() != null) {
                List<String> attributeList = generateAttributeList(extendedAdObject.getAdObjectCategory().getFullname());
                model.addAttribute("attributeList", attributeList);
            }
            List<ObjectAttribute> objectAttrList = parser.retrieveAttributeList();
            List<String> attributeList = new ArrayList<>();
            for (ObjectAttribute objectAttribute : objectAttrList) {
                log.debug(objectAttribute.getName()+ "   " + objectAttribute.getLabel() + "   " + objectAttribute.getType() );
                attributeList.add(objectAttribute.getName());
            }
            model.addAttribute("attributeList", attributeList);


            String stringAdId = String.valueOf(adId);
            List<BookingOrderDetail> bookingOrderDetails = retrieveBookingOrderSearch(stringAdId);

            Collections.sort(bookingOrderDetails);
            model.addAttribute("bookingOrderDetail", bookingOrderDetails);


            model.addAttribute("bookingOrderDetailSize", bookingOrderDetails.size());
            log.debug("BookingORderDetailSize ::::" + bookingOrderDetails.size());

            FraudLogObjectResponse fraudLogObjectResponse = fraudLogService.searchAdObjectId(adId);
            if (fraudLogObjectResponse.getZettFraudLogObjects() != null) {
                log.debug("Size = " + fraudLogObjectResponse.getZettFraudLogObjects().size());

                //TODO REVIEW 1/17/13, refactor, you use FOR EACH but do not actually use it
                for (ZettFraudLogObject zettFraudLogObject : fraudLogObjectResponse.getZettFraudLogObjects()) {

                    //TODO REVIEW 1/17/13, refactor, this block is just print the last item in list, no need to be in the loop
                    if (!fraudLogObjectResponse.getZettFraudLogObjects().isEmpty()) {
                        int index = fraudLogObjectResponse.getZettFraudLogObjects().size();
                        log.debug("Index Origin = " + index);
                        index = (index) - 1;
                        log.debug("Status = " + fraudLogObjectResponse.getZettFraudLogObjects().get(index).getStatus());
                        log.debug("LogId = " + fraudLogObjectResponse.getZettFraudLogObjects().get(index).getLogId());
                        model.addAttribute("fraudLogStatus", fraudLogObjectResponse.getZettFraudLogObjects().get(index).getStatus());
                    }
                }

                List<ZettPrintAdObject> zettPrintAdObjectList = new ArrayList<ZettPrintAdObject>();
                AdBookingResponse adBookingResponse = bookingAdminService.getAdBookingByAdObjectId(stringAdId);
                ZettAdBooking zettAdBooking = adBookingResponse.getZettAdBooking();

                if (zettAdBooking != null && zettAdBooking.getZettAdBookingItems().size() != 0) {
                    log.debug(" ZettAdBooking " + zettAdBooking.getBookingId());
                    for (ZettAdBookingItem zettAdBookingOrderItem : zettAdBooking.getZettAdBookingItems()) {
                        if (ObjectDataTableType.PRINTAD.toString().equals(zettAdBookingOrderItem.getDataTableType().value())) {
                            log.debug(" Datatype " + zettAdBookingOrderItem.getDataTableType().value());
                            log.debug("Having PrintAd");
                            PrintAdObjectResponse printAdObjectResponse = printAdService.loadPrintAdById(zettAdBookingOrderItem.getObjectId());
                            ZettPrintAdObject zettPrintAdObject = printAdObjectResponse.getZettPrintAdObject();
                            zettPrintAdObjectList.add(zettPrintAdObject);

                        }
                    }
                }
                Map<Integer, String> printAdObjectMap = new HashMap<Integer, String>();
                if (zettPrintAdObjectList.size() != 0) {
                    for (ZettPrintAdObject zettPrintAdObject : zettPrintAdObjectList) {
                        log.debug("PrintAdID = " + zettPrintAdObject.getObjectId());
                        for (ZettObjectAttribute zettObjectAttribute : zettPrintAdObject.getAttributes()) {
                            if (zettObjectAttribute.getName().equals("printad_newspaperurl")) {
                                printAdObjectMap.put(zettPrintAdObject.getObjectId(), zettObjectAttribute.getValue());

                            }
                        }
                    }
                }
                model.addAttribute("printadobjectmap", printAdObjectMap);
                model.addAttribute("mapsize", printAdObjectMap.size());

                log.debug("No Bad Word, Primary Company ID = " + zettAdObject.getCompany().getObjectId());

                log.debug("Check zettAdObjectAdditionalCompany ");
                List<ZettAdObjectAdditionalCompany> zettAdObjectAdditionalCompanyList = zettAdObject.getAdObjectAdditionalCompanies();
                Collections.sort(zettAdObjectAdditionalCompanyList, new ComparatorCompanyId());
                StringBuilder additionalCompanyIdList = new StringBuilder();

                if (zettAdObjectAdditionalCompanyList != null) {
                    boolean isFirst = true;
                    for (ZettAdObjectAdditionalCompany zettAdObjectAdditionalCompany : zettAdObjectAdditionalCompanyList) {
                        if (isFirst) {
                            isFirst = false;
                        } else {
                            additionalCompanyIdList.append(",");
                        }
                        additionalCompanyIdList.append(zettAdObjectAdditionalCompany.getCompanyId());

                        log.debug("zettAdObjectAdditionalCompany, size = {}, ID = {}, Ad ID = {}",
                                zettAdObjectAdditionalCompanyList.size(),
                                zettAdObjectAdditionalCompany.getCompanyId(),
                                zettAdObjectAdditionalCompany.getObjectId());
                    }
                    model.addAttribute("AdditionalCompanyIdList", additionalCompanyIdList);

                } else {
                    log.debug("No zettAdObjectAdditionalCompany");
                }

                model.addAttribute("imageVersion", zeelandProperties.getObscuraImageVersion());

                if (updateMessage != null) {
                    model.addAttribute("updateMessage", updateMessage);
                }
                if (error != null) {
                    model.addAttribute("error", error);
                }
            }

        } catch (ZeelandException ex) {
            model.addAttribute("errorMessage", "validation.ad.either.found.nor.connect.webservice");
        }


        return SHOWAD;
    }

    private List<String> generateAttributeList(String category) {
        List<String> attributeList = new ArrayList<String>();
        List<AttributeObject> attributeObjectList;
        if (category.startsWith("Motor")) {
            attributeObjectList = attributesGenerator.find(AttributeGroup.AUTO);
        } else if (category.startsWith("Eiendom")) {
            attributeObjectList = attributesGenerator.find(AttributeGroup.PROPERTY);
        } else if (category.startsWith("Stilling")) {
            attributeObjectList = attributesGenerator.find(AttributeGroup.JOB);
        } else if (category.startsWith("Båt")) {
            attributeObjectList = attributesGenerator.find(AttributeGroup.BOAT);
        } else {
            attributeObjectList = attributesGenerator.find(AttributeGroup.COMMON);
        }

        if (attributeList != null) {
            for (AttributeObject attributeObject : attributeObjectList) {
                attributeList.add(attributeObject.getName());
            }
            Collections.sort(attributeList);
        } else {
            attributeList = new ArrayList<String>();
        }

        return attributeList;
    }

    private List<BookingOrderDetail> retrieveBookingOrderSearch(String stringAdId) throws ServiceException_Exception {
        BookingOrderSearchObjectResponse bookingOrderSearchObjectResponse = bookingAdminService.searchByAdObjectID(stringAdId);
        List<ZettBookingOrderSearchObject> zettBookingOrderSearchObjectList = bookingOrderSearchObjectResponse.getZettBookingOrderSearchObjects();
        List<BookingOrderDetail> bookingOrderDetails = new ArrayList<BookingOrderDetail>();
        for (ZettBookingOrderSearchObject zettBookingOrderSearchObject : zettBookingOrderSearchObjectList) {
            log.debug("OrderDate" + zettBookingOrderSearchObject.getOrderDate());
            log.debug("OrderID" + zettBookingOrderSearchObject.getOrderId());
            BookingOrderDetail bookingOrderDetail = new BookingOrderDetail();
            List<BookingOrderItems> bookingOrderItemsList = new ArrayList<BookingOrderItems>();
            XMLGregorianCalendar xmlGregorianCalendar = zettBookingOrderSearchObject.getOrderDate();
            StringBuilder orderDate = new StringBuilder();

            String day = Integer.toString(xmlGregorianCalendar.getDay());
            day = (day.length() == 1) ? "0" + day : day;
            String month = Integer.toString(xmlGregorianCalendar.getMonth());
            month = (month.length() == 1) ? "0" + month : month;
            orderDate.append(day).append(".").append(month).append(".").append(xmlGregorianCalendar.getYear());
            bookingOrderDetail.setOrderDate(orderDate.toString());
            bookingOrderDetail.setOrderId(zettBookingOrderSearchObject.getOrderId());
            bookingOrderDetail.setCurrencyType(CurrencyType.fromInteger(zettBookingOrderSearchObject.getCurrencyType()).getDescription());
            bookingOrderDetail.setTotalCost(zettBookingOrderSearchObject.getTotalCost());


            for (ZettAdBookingOrderItem zettAdBookingOrderItem : zettBookingOrderSearchObject.getZettAdBookingOrderItems()) {
                BookingOrderItems bookingOrderItems = new BookingOrderItems();
                bookingOrderItems.setOrderType(OrderItemType.fromInteger(zettAdBookingOrderItem.getOrderItemType()).getTitle());
                bookingOrderItems.setPrice(zettAdBookingOrderItem.getPrice());
                bookingOrderItemsList.add(bookingOrderItems);
            }
            bookingOrderDetail.setBookingOrderItemses(bookingOrderItemsList);
            bookingOrderDetail = retrievePaymentInformation(bookingOrderDetail);
            bookingOrderDetails.add(bookingOrderDetail);

        }
        return bookingOrderDetails;
    }

    @RequestMapping(value = "savead.html")
    public String saveAd(Model model, @ModelAttribute(EXTENDED_AD_OBJECT) ExtendedAdObject extendedAdObject,
                         BindingResult result) throws
            DatatypeConfigurationException, IOException, ServiceException_Exception, ParseException {

        saveAdValidation.validate(extendedAdObject, result);
        if (result.hasErrors()) {
            model.addAttribute(EXTENDED_AD_OBJECT, extendedAdObject);
            webserviceAuthentication.authentication(adServicePortType);
            AdObjectResponse adObjectResponse = adServicePortType.loadObject(extendedAdObject.getObjectId());
            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
            model.addAttribute(ZETTADOBJECT, zettAdObject);
            model.addAttribute(CATEGORYMAP, categoryBuilder.getCategoryMap());
            model.addAttribute(TRANSITIONURL, zeelandProperties.getTransitionURL());
            return SHOWAD;
        } else {
            webserviceAuthentication.authentication(adServicePortType);
            ZettAdObject zettAdObject = adUpdate.update(extendedAdObject);
            updateAdImage(zettAdObject, extendedAdObject);
            try {
                adServicePortType.saveObject(zettAdObject);
                AdObjectResponse adObjectResponseAfterSave = adServicePortType.loadObject(extendedAdObject.getObjectId());
                zettAdObject = adObjectResponseAfterSave.getZettAdObject();
                reOrderImage(zettAdObject, extendedAdObject);
                adServicePortType.saveObject(zettAdObject);
            } catch (ServiceException_Exception e) {
                model.addAttribute(ZETTADOBJECT, zettAdObject);
                model.addAttribute("error", "showad.message.error.save.ad");
                model.addAttribute(CATEGORYMAP, categoryBuilder.getCategoryMap());
                model.addAttribute(TRANSITIONURL, zeelandProperties.getTransitionURL());
                return SHOWAD;
            }

            log.debug("After save there are   " + zettAdObject.getMedia().size() + "  images");
            model.addAttribute(ZETTADOBJECT, zettAdObject);
            model.addAttribute(EXTENDED_AD_OBJECT, new ExtendedAdObjectTransformer().transform(zettAdObject));
            model.addAttribute(CATEGORYMAP, categoryBuilder.getCategoryMap());
            model.addAttribute("updateMessage", "showad.message.updated");
            model.addAttribute(TRANSITIONURL, zeelandProperties.getTransitionURL());
            log.debug("Modify by : " + zettAdObject.getModifiedBy());
            return REDIRECTSHOWAD + zettAdObject.getObjectId();

        }
    }

    @RequestMapping("showAdMap.html")
    public String showAdMap(Model model, @RequestParam(value = "mapadid", required = false) Integer adId)
            throws ServiceException_Exception {
        log.debug(ADID + " : " + adId);
        webserviceAuthentication.authentication(adServicePortType);
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        log.debug("postcode : " + adObjectResponse.getZettAdObject().getAddress().getPostCode());
        if (adObjectResponse.getZettAdObject().getAddress().getPostCode() != null && !adObjectResponse.getZettAdObject().getAddress().getPostCode().isEmpty()) {
            ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
            adObjectResponse = adServicePortType.mapLookUp(zettAdObject);
            zettAdObject = adObjectResponse.getZettAdObject();
            adObjectResponse = adServicePortType.saveObject(zettAdObject);

            model.addAttribute(ZETTADOBJECT, zettAdObject);
            model.addAttribute(EXTENDED_AD_OBJECT, new ExtendedAdObjectTransformer().transform(zettAdObject));
            model.addAttribute(CATEGORYMAP, categoryBuilder.getCategoryMap());


            return "redirect:showad.html?adId=" + zettAdObject.getObjectId() + "&updateMessage=showad.message.map.lookup.pending";
        } else {
            log.debug("No Postnumber");
            return REDIRECTSHOWAD + adId + "&error=validation.emptyField.company.postCode";
        }
    }

    @RequestMapping("setmainmediaad.html")
    public String setMainMediaAd(@RequestParam(value = ADID, required = true) int adId,
                                 @RequestParam(value = "mediaId", required = true) int mediaId) {

        log.debug("set main media page with aid = " + adId + "and media id = " + mediaId);

        try {
            ZettAdObject zettAdObject = adUpdate.setMainMedia(adId, mediaId);
            for (ZettObjectMedia zettObjectMedia : zettAdObject.getMedia()) {
                log.debug("found media id = " + zettObjectMedia.getMediaId() + " order = " + zettObjectMedia.getOrder());
            }
        } catch (ServiceException_Exception err) {
            log.debug("can not set new main media for ad id = " + adId + " :" + err.getMessage());
        }

        return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
    }

    private void updateAdImage(ZettAdObject zettAdObject, ExtendedAdObject extendedAdObject) throws IOException, ServiceException_Exception, ParseException {

        MultipartFile[] adImages = extendedAdObject.getAdImgs();
        if (adImages != null && adImages.length > 0) {
            for (MultipartFile file : adImages) {
                if (file.getSize() > 0) {
                    ZettObjectMedia zettObjectMedia = new ZettObjectMedia();
                    zettObjectMedia.setType(ObjectMediaType.IMAGE.toInteger());
                    zettObjectMedia.setOrder(zettAdObject.getMedia().size() + 1);
                    zettObjectMedia.setImagebuffer(file.getBytes());
                    zettObjectMedia.setTitle(file.getOriginalFilename());
                    zettAdObject.getMedia().add(zettObjectMedia);
                }
            }
        }
    }

    @RequestMapping("deleteMediaAd.html")
    public String deleteMediaAd(@RequestParam(value = "adIdForDeleteImage", required = true) int adId,
                                @RequestParam(value = "mediaIdForDelete", required = true) int mediaId) throws ServiceException_Exception {
        webserviceAuthentication.authentication(adServicePortType);
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
        log.debug("Before delete  there are  " + zettAdObject.getMedia().size() + "images");

        try {
            zettAdObject = adUpdate.deleteMedia(adId, mediaId);

            log.debug("After delete  there are  " + zettAdObject.getMedia().size() + "images");

        } catch (ServiceException_Exception err) {
            log.debug("can not set new main media for ad id = " + adId + " :" + err.getMessage());
        }

        return REDIRECTSHOWAD + adId;
    }


    private void reOrderImage(ZettAdObject zettAdObject, ExtendedAdObject extendedAdObject) {
        String imageOrder = extendedAdObject.getImageOrder();
        if (imageOrder != null && !"".equals(imageOrder)) {
            String[] orderArray = imageOrder.split(",");
            List<ZettObjectMedia> objectMediasList = zettAdObject.getMedia();
            //List<ZettObjectMedia> orderedMediaList = new ArrayList<ZettObjectMedia>();
            for (int i = 0; i < orderArray.length; i++) {
                String ref = orderArray[i];
                for (ZettObjectMedia media : objectMediasList) {
                    if (ref.equals(media.getReference())) {
                        media.setOrder(i + 1);
                        break;
                    }
                }
            }

            objectMediasList = Collections.emptyList();
            //orderArray = null;
        }

    }

    private String createZettUrl(String categoryParam, int objectId) {
        String category = categoryParam;
        if (category != null && !"".equals(category)) {
            if (category.startsWith("Motor")) {
                category = "motorprospekt";
            } else if (category.startsWith("Båt")) {
                category = "baatprospekt";
            } else if (category.startsWith("Småttogstort")) {
                category = "torgprospekt";
            } else if (category.startsWith("Eiendom")) {
                category = "eiendomsprospekt";
            } else if (category.startsWith("Stilling")) {
                category = "jobbprospekt";
            } else if (category.startsWith("Frivillig")) {
                category = "frivilligprospekt";

            }
        }
        log.debug("This ad does not have category");
        String zettUrl = zeelandProperties.getZettUrl() + "/vis/rubrikk/" + category + "/" + objectId + ".html";
        return zettUrl.trim();
    }

    @RequestMapping("deleteadattribute.html")
    public String deleteAdAttribute(@RequestParam(value = "attributeid", required = true) int attributeId,
                                    @RequestParam(value = "adid", required = true) int adId) throws ServiceException_Exception {

        log.debug("delete attribute id = " + attributeId);
        AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
        if (zettAdObject != null) {
            List<ZettObjectAttribute> attributeList = zettAdObject.getAttributes();
            if (attributeList != null) {
                log.debug("attribute list is not null");
                ZettObjectAttribute targetAttribute = null;
                for (ZettObjectAttribute zettObjectAttribute : attributeList) {
                    if (zettObjectAttribute.getAttributeId() == attributeId) {
                        log.debug("found this id in attribute list");
                        targetAttribute = zettObjectAttribute;
                    }
                }
                if (targetAttribute != null) {
                    log.debug("deleting attribute id = " + attributeId);
                    zettAdObject.getAttributes().remove(targetAttribute);
                    adServicePortType.saveObject(zettAdObject);
                }
            }
        }
        return REDIRECTSHOWAD + adId + "&updateMessage=showad.message.updated";
    }

    /*
      To get the payment information and display in Ordreinformasjon table in showad page
     */
    private BookingOrderDetail retrievePaymentInformation(BookingOrderDetail bookingOrderDetail) throws ServiceException_Exception {
        BookingOrderDetail result = bookingOrderDetail;
        if (bookingOrderDetail != null && bookingOrderDetail.getOrderId() != null) {
            AdBookingOrderResponse adBookingOrderResponse = bookingAdminService.getAdBookingOrderByOrderId(bookingOrderDetail.getOrderId());
            ZettAdBookingOrderObject zettAdBookingOrderObject = adBookingOrderResponse.getZettAdBookingOrderObject();
            for (ZettBookingOrderAttribute zettBookingOrderAttribute : zettAdBookingOrderObject.getZettBookingOrderAttributes()) {
                if (zettBookingOrderAttribute.getName().equals("payment_type")) {
                    result.setPaidBy(zettBookingOrderAttribute.getValue());
                }
            }
            if (zettAdBookingOrderObject.getBookingDiscountId() != null) {
                BookingDiscountResponse bookingDiscountResponse = bookingAdminService.getBookingDiscountBybookingDiscountId(zettAdBookingOrderObject.getBookingDiscountId());
                ZettBookingDiscount zettBookingDiscount = bookingDiscountResponse.getZettBookingDiscount();
                result.setGeneratedCode(zettBookingDiscount.getGeneratedCode());
                result.setPercent(zettBookingDiscount.getPercent());
            }
        }
        return result;
    }
}

class ComparatorCompanyId implements Comparator<ZettAdObjectAdditionalCompany> {
    @Override
    public int compare(ZettAdObjectAdditionalCompany o1, ZettAdObjectAdditionalCompany o2) {
        return o1.getCompanyId() - o2.getCompanyId();
    }
}


