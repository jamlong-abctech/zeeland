package com.abctech.zeeland.controller;

import com.abctech.zeeland.form.SearchCompany;
import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.form.data.ExtendedCompanyObject;
import com.abctech.zeeland.form.util.PageCalculator;
import com.abctech.zeeland.form.util.PageInformation;
import com.abctech.zeeland.form.util.ZeelandProperties;
import com.abctech.zeeland.form.validation.SaveCompanyValidation;
import com.abctech.zeeland.form.validation.SearchCompanyValidation;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.initialize.ContactInitializer;
import com.abctech.zeeland.wsObject.transform.adobject.ExtendedAdObjectTransformer;
import com.abctech.zeeland.wsObject.transform.base.ObjectMediaListTransformer;
import com.abctech.zeeland.wsObject.transform.companyobject.ExtendedCompanyObjectTransformer;
import com.abctech.zeeland.wsObject.update.AdUpdate;
import com.abctech.zeeland.wsObject.update.CompanyUpdate;
import no.zett.model.base.Contact;
import no.zett.model.base.ContactAttribute;
import no.zett.model.base.ObjectMedia;
import no.zett.model.enums.ContactAttributeType;
import no.zett.model.enums.ObjectMediaType;
import no.zett.service.facade.AdObjectResponse;
import no.zett.service.facade.AdService;
import no.zett.service.facade.CompanyObjectResponse;
import no.zett.service.facade.CompanyService;
import no.zett.service.facade.SearchItemResponse;
import no.zett.service.facade.SearchItemService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettContact;
import no.zett.service.facade.ZettObjectAttribute;
import no.zett.service.facade.ZettObjectMedia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CompanyController {

    private final static Logger log = LoggerFactory.getLogger(CompanyController.class);
    private static final String ZETT_COMPANY_OBJECT = "zettCompanyObject";
    private static final String COMPANY = "company";
    private static final String SHOW_COMPANY = "showcompany";
    private static final String SEARCH_COMPANY = "searchcompany";
    private static final String COMPANYID = "companyId";
    public static final String UPDATE_MESSAGE = "updateMessage";
    public static final String SEARCHCOMPANYMODEL = "searchCompany";
    private final static String ZSERVICESURL = "zservicesurl" ;

    @Autowired
    private CompanyUpdate companyUpdate;

    @Autowired
    private AdUpdate adUpdate;

    @Autowired
    private AdService adServicePortType;

    @Autowired
    @Qualifier(value = "saveCompanyValidation")
    private SaveCompanyValidation saveCompanyValidation;

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private CompanyService companyServicePortType;

    @Autowired
    private SearchItemService searchItemServicePortType;

    @Autowired
    @Qualifier(value = "searchCompanyValidation")
    private SearchCompanyValidation searchCompanyValidation;

    @Autowired
    private ZeelandProperties zeelandProperties;



    @RequestMapping(value = "searchcompany.html")
    public ModelAndView searchCompany(@ModelAttribute(SEARCHCOMPANYMODEL) SearchCompany searchCompany,
                                      BindingResult result
    ) throws ServiceException_Exception, IOException {


        ModelAndView modelAndView = new ModelAndView();
        searchCompanyValidation.validate(searchCompany, result);
        if (!result.hasErrors()) { // No error
            if (searchCompany.getCompanyId() != null && !searchCompany.getCompanyId().isEmpty()) {
                // The valueOf will work as we have validated the field in the validator.
                Integer companyId = Integer.valueOf(searchCompany.getCompanyId());
                modelAndView.addObject(COMPANYID, companyId);

                modelAndView.setViewName("forward:showcompany.html");
                return modelAndView;
            } else if (searchCompany.getTitle() != null && !searchCompany.getTitle().isEmpty()) {
                // String title = searchCompany.getTitle();
                SearchItemResponse searchItemResponse = null;
                if ("all".equals(searchCompany.getStatus())) {
                    searchItemResponse = searchItemServicePortType.titleSearchCompany(searchCompany.getTitle(), null, 1);
                } else {
                    searchItemResponse = searchItemServicePortType.titleSearchCompany(searchCompany.getTitle(), searchCompany.getStatus(), 1);
                }
                return createComapnyViewResultSearch(modelAndView, searchItemResponse, searchCompany.getTitle(), "1");
            }


        }

        modelAndView.setViewName(SEARCH_COMPANY);
        return modelAndView;
    }


    @RequestMapping(value = "searchresultbypage.html")
    public ModelAndView searchresultbypage(@ModelAttribute(SEARCHCOMPANYMODEL) SearchCompany searchCompany,
                                           BindingResult result,
                                           @RequestParam(value = "totalPage", required = false) Integer totalPage,
                                           @RequestParam(value = "companyTitleSearch", required = false) String companytitle,
                                           @RequestParam(value = "totalResult", required = false) String totalResult,
                                           @RequestParam(value = "status", required = false) String status,
                                           @RequestParam(value = "pageSearch", required = false) Integer page) throws ServiceException_Exception {
        log.debug("##### Show Result By Page ######");
        log.debug("Page : " + page);
        log.debug("Company Title: " + companytitle);
        log.debug("pageTotal:  " + totalPage);

        ModelAndView modelAndView = new ModelAndView();

        searchCompany.setTitle(companytitle);

        SearchItemResponse searchItemResponse = null;
        if("all".equals(status)) {
            searchItemResponse = searchItemServicePortType.titleSearchCompany(companytitle,null, page);
        } else {
            searchItemResponse = searchItemServicePortType.titleSearchCompany(companytitle,status, page);
        }

        modelAndView.addObject("zettSearchItems", searchItemResponse.getZettSearchItems());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("totalResult", totalResult);
        modelAndView.addObject("currentPage", page);
        modelAndView.setViewName("showlistcompany");

        return modelAndView;


    }


    @RequestMapping(value = "getcompany.html")
    public ModelAndView getCompany(@RequestParam(value = COMPANYID, required = false) Integer companyId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(COMPANYID, companyId);
        modelAndView.setViewName("forward:showcompany.html");
        return modelAndView;
    }


    @RequestMapping(value = "showcompany.html")
    public ModelAndView showCompany(@RequestParam(value = COMPANYID, required = false) Integer companyId,
                                    @RequestParam(value = "status", required = false) String status,
                                    @RequestParam(value = "updateMessage", required = false) String updateMessage){


        ModelAndView modelAndView = new ModelAndView();
        try {

            //modelAndView.addObject("categoryMap",createFirmaerCategory());
            webserviceAuthentication.authentication(companyServicePortType);
            CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);

            if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject() != null) {

                ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
                List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();
                if (zettObjectMediaList != null) {
                    log.debug("===== found media = " + zettObjectMediaList.size());
                    int i = 1;
                    for (ZettObjectMedia zettObjectMedia : zettObjectMediaList) {
                        log.debug(i + ".");
                        log.debug("title = " + zettObjectMedia.getTitle());
                        log.debug("description = " + zettObjectMedia.getDescription());
                        log.debug("reference=" + zettObjectMedia.getReference());
                        log.debug("type = " + ObjectMediaType.fromInteger(zettObjectMedia.getType()).name());
                    }
                }

                ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);
                //add new contact model for add
                Contact contact = new ContactInitializer().initialize();
                extendedCompanyObject.setExtendedContact(contact);
                log.debug("Status :::: " + extendedCompanyObject.getStatus());

                log.debug("Package :::::" + extendedCompanyObject.getPackageDealTypeValue());
                modelAndView.addObject(COMPANY, extendedCompanyObject);
                modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
                modelAndView.addObject("transitionURL", zeelandProperties.getTransitionURL());
                modelAndView.addObject("imageLogoVersion", zeelandProperties.getObscuraImageLogoVersion());
                modelAndView.addObject(ZSERVICESURL , zeelandProperties.getZservicesurlpaht())   ;
                if (updateMessage != null) {
                    modelAndView.addObject(UPDATE_MESSAGE, updateMessage);
                }

                if (extendedCompanyObject.getCategory().contains("Eiendom")) {

                    modelAndView.addObject("adURL", zeelandProperties.getPropertyUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());
                } else {

                    modelAndView.addObject("adURL", zeelandProperties.getZettUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());

                }

                modelAndView.setViewName("showcompany");
                return modelAndView;
            } else {
                return createNotFoundModel(companyId);
            }
        } catch (ServiceException_Exception err) {
            return createNotFoundModel(companyId);
        }

    }

    @RequestMapping(value = "addcompany.html")
    public ModelAndView addNewCompany() {
        ModelAndView modelAndView = new ModelAndView();
        ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObject();
        Contact contact = new ContactInitializer().initialize();
        extendedCompanyObject.setExtendedContact(contact);
        modelAndView.addObject(COMPANY, extendedCompanyObject);
        modelAndView.setViewName(SHOW_COMPANY);
        return modelAndView;
    }

    @RequestMapping(value = "savecompany.html")
    public ModelAndView saveCompany(@ModelAttribute(COMPANY) ExtendedCompanyObject extendedCompanyObject
            , BindingResult result
            , @RequestParam(value = "fileCompanyLogo", required = false) MultipartFile fileCompanyLogo
            , @RequestParam(value = "skipValidator", required = false) String isSkipValidator) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(SHOW_COMPANY);
        ZettCompanyObject zettCompanyObject = null;
        ExtendedCompanyObject extendedCompany = null;
        Integer companyObjectId = extendedCompanyObject.getObjectId();
        if (extendedCompanyObject.getPackageDealTypeValue() == null) {
            extendedCompanyObject.setPackageDealTypeValue(0);
        }
        log.debug("CompanyObjectId :::::::" + companyObjectId);

        if (!"true".equals(isSkipValidator)) {
            saveCompanyValidation.validate(extendedCompanyObject, result);
        }
        if (result.hasErrors()) {
            if (companyObjectId != null) {
                modelAndView.addObject("overrideValidator", "overrideValidator");
            }
            //modelAndView.addObject(COMPANY, extendedCompanyObject);
            modelAndView = addZettCompanyData(modelAndView, extendedCompanyObject);
            return modelAndView;
        } else {
            try {
                webserviceAuthentication.authentication(companyServicePortType);
                if (extendedCompanyObject.getObjectId() != null && extendedCompanyObject.getObjectId() > 0) {

                    zettCompanyObject = companyUpdate.update(extendedCompanyObject);

                    //update with logo
                    zettCompanyObject = updateCompanyLogo(fileCompanyLogo, zettCompanyObject);

                    //convert the zett company object to company object
                    extendedCompany = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);

                    //add object to view
                    log.debug("updated company id = " + zettCompanyObject.getObjectId());

                    modelAndView.setViewName("redirect:showcompany.html?companyId=" + extendedCompany.getObjectId() + "&updateMessage=showcompany.message.updated");
                    return modelAndView;
                } else {


                    if (!fileCompanyLogo.isEmpty()) {
                        log.debug("New Company With Logo");
                        zettCompanyObject = updateCompanyLogo(fileCompanyLogo, companyUpdate.add(extendedCompanyObject));
                    } else {
                        log.debug("New Comapny No Logo");
                        zettCompanyObject = companyUpdate.add(extendedCompanyObject);
                    }


                    //convert the zett company object to company object
                    extendedCompany = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);
                    //add object to view.
                    log.debug("save company completed and company id = " + zettCompanyObject.getObjectId());

                    modelAndView.setViewName("redirect:showcompany.html?companyId=" + extendedCompany.getObjectId() + "&updateMessage=showcompany.message.added");
                    return modelAndView;
                }
            } catch (ServiceException_Exception err) {
                log.error("has a problem when try to save this company to service : " + err.getMessage(), err);
                result.addError(generateErrorWithException(err));
            } catch (SOAPFaultException err) {
                log.error("experienced problem with the soap connection", err);
                result.addError(generateErrorWithException(err));
            } catch (IOException err) {
                log.error("Got an unexpected IO exception", err);
                result.addError(generateErrorWithException(err));
            }
            modelAndView.addObject(COMPANY, extendedCompany);
            if (zettCompanyObject == null) {
                modelAndView = addZettCompanyData(modelAndView, extendedCompany);
            }
            return modelAndView;
        }
    }

    @RequestMapping(value = "reindex.html")
    public ModelAndView reindexCompanysAds(@RequestParam(value = "reindexid", required = false) Integer companyId) {
        log.debug("Start reindexing all active ads of company : " + companyId);
        ModelAndView modelAndView = new ModelAndView();
        try {
            webserviceAuthentication.authentication(companyServicePortType);
            List<Integer> adObjectIds = companyServicePortType.retrieveCompanyActiveAdsIds(companyId);
            for (Integer adId : adObjectIds) {
                AdObjectResponse adObjectResponse = adServicePortType.loadObject(adId);
                ZettAdObject zettAdObject = adObjectResponse.getZettAdObject();
                ExtendedAdObject extendedAdObject = new ExtendedAdObjectTransformer().transform(zettAdObject);
                zettAdObject = adUpdate.update(extendedAdObject);
                log.debug("update AdObject ID : " + adId);
                log.debug("System time = " + zettAdObject.getSystemModifiedTime());
                adServicePortType.saveObject(zettAdObject);
            }
            CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
            if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject() != null) {

                ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
                List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();
                if (zettObjectMediaList != null) {
                    log.debug("===== found media = " + zettObjectMediaList.size());
                    int i = 1;
                    for (ZettObjectMedia zettObjectMedia : zettObjectMediaList) {
                        log.debug(i + ".");
                        log.debug("title = " + zettObjectMedia.getTitle());
                        log.debug("description = " + zettObjectMedia.getDescription());
                        log.debug("type = " + ObjectMediaType.fromInteger(zettObjectMedia.getType()).name());
                    }
                }

                ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);
                //add new contact model for add
                Contact contact = new ContactInitializer().initialize();
                extendedCompanyObject.setExtendedContact(contact);
                log.debug("Status :::: " + extendedCompanyObject.getStatus());

                log.debug("Package :::::" + extendedCompanyObject.getPackageDealTypeValue());
                modelAndView.addObject(COMPANY, extendedCompanyObject);
                modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
                modelAndView.addObject("transitionURL", zeelandProperties.getTransitionURL());
                if (extendedCompanyObject.getCategory().contains("Eiendom")) {

                    modelAndView.addObject("adURL", zeelandProperties.getPropertyUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());
                } else {

                    modelAndView.addObject("adURL", zeelandProperties.getZettUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());

                }

                modelAndView.setViewName("redirect:showcompany.html?companyId=" + companyId + "&updateMessage=showcompany.message.advertise");
            }
        } catch (ServiceException_Exception err) {
            log.error("has a problem when try to save this ads to service : " + err.getMessage(), err);
        } catch (ParseException err) {
            log.error("has a problem when try to save this ads to service : " + err.getMessage(), err);
        }
        return modelAndView;
    }

    @RequestMapping(value = "deactivatecompanyads.html")
    public ModelAndView deactivateCompanyAds(@RequestParam(value = "deactivateid", required = false) Integer companyId) {
        log.debug("Start deactivate all ads of company : " + companyId);
        ModelAndView modelAndView = new ModelAndView();
        try {
            webserviceAuthentication.authentication(companyServicePortType);
            companyServicePortType.deactivateCompanyAds(companyId);
            CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
            if (companyObjectResponse.getZettCompanyObject() != null && companyObjectResponse.getZettCompanyObject() != null) {

                ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
                List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();
                if (zettObjectMediaList != null) {
                    log.debug("===== found media = " + zettObjectMediaList.size());
                    int i = 1;
                    for (ZettObjectMedia zettObjectMedia : zettObjectMediaList) {
                        log.debug(i + ".");
                        log.debug("title = " + zettObjectMedia.getTitle());
                        log.debug("description = " + zettObjectMedia.getDescription());
                        log.debug("type = " + ObjectMediaType.fromInteger(zettObjectMedia.getType()).name());
                    }
                }

                ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);
                //add new contact model for add
                Contact contact = new ContactInitializer().initialize();
                extendedCompanyObject.setExtendedContact(contact);
                log.debug("Status :::: " + extendedCompanyObject.getStatus());

                log.debug("Package :::::" + extendedCompanyObject.getPackageDealTypeValue());
                modelAndView.addObject(COMPANY, extendedCompanyObject);
                modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
                modelAndView.addObject("transitionURL", zeelandProperties.getTransitionURL());
                if (extendedCompanyObject.getCategory().contains("Eiendom")) {

                    modelAndView.addObject("adURL", zeelandProperties.getPropertyUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());
                } else {

                    modelAndView.addObject("adURL", zeelandProperties.getZettUrl().trim() + "?compId=".replaceAll(" ", "") + zettCompanyObject.getObjectId());

                }

                modelAndView.setViewName("redirect:showcompany.html?companyId=" + companyId + "&updateMessage=showcompany.message.advertise");
            }
        } catch (ServiceException_Exception err) {
            log.error("has a problem when try to save this ads to service : " + err.getMessage(), err);
        }
        return modelAndView;
    }

    private ZettCompanyObject updateCompanyLogo(MultipartFile fileCompanyLogo, ZettCompanyObject zettCompanyObject) throws IOException, ServiceException_Exception {


        if ( zettCompanyObject != null) {
            List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();

            log.debug("****************** file name = " + fileCompanyLogo.getName());
            log.debug("****************** origin file name = " + fileCompanyLogo.getOriginalFilename());

            log.debug("call update company logo method ");

            //in case that has media already
            if (zettObjectMediaList != null && zettObjectMediaList.size() > 0) {
                ZettObjectMedia zettObjectMediaRemove = null;
                for (ZettObjectMedia zettObjectMedia : zettObjectMediaList) {
                    //check has old logo
                    if (zettObjectMedia.getType() == ObjectMediaType.LOGO.toInteger()) {
                        zettObjectMediaRemove = zettObjectMedia;
                    }
                }
                if (zettObjectMediaRemove != null) {
                    zettCompanyObject.getMedia().remove(zettObjectMediaRemove);
                }

                ZettObjectMedia zettObjectMedia = createLogoObjectForCompany(zettCompanyObject, fileCompanyLogo.getBytes(), fileCompanyLogo.getOriginalFilename());
                zettCompanyObject.getMedia().add(zettObjectMedia);
            } else {
                //not have any object media in this zett company object
                ZettObjectMedia zettObjectMedia = createLogoObjectForCompany(zettCompanyObject, fileCompanyLogo.getBytes(), fileCompanyLogo.getOriginalFilename());
                zettCompanyObject.getMedia().add(zettObjectMedia);
            }

            CompanyObjectResponse newCompanyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
            if (newCompanyObjectResponse != null && newCompanyObjectResponse.getZettCompanyObject() != null) {

                log.debug("save company logo success ...");
                //zettCompanyObject = newCompanyObjectResponse.getZettCompanyObject().getValue();      fix sonar
                return newCompanyObjectResponse.getZettCompanyObject();
            } else {
                log.debug("companyServicePortType return null ....");
            }
        }


        return zettCompanyObject;
    }

    private ZettObjectMedia createLogoObjectForCompany(ZettCompanyObject zettCompanyObject
            , byte[] imageBuffer
            , String fileName) {
        log.debug("try to upload the media logo ... ");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm");

        ZettObjectMedia zettObjectMedia = new ZettObjectMedia();
        zettObjectMedia.setImagebuffer(imageBuffer);
        zettObjectMedia.setCompanyid(zettCompanyObject.getObjectId());
        zettObjectMedia.setType(ObjectMediaType.LOGO.toInteger());
        zettObjectMedia.setOrder(1);
        log.debug("#....... file name =" + fileName);
//        String[] token = fileName.split(".");
//        if(token.length>0){
//           fileName = token[0]+"_"+simpleDateFormat.format(date)+token[1];
//        }else{
//            fileName = token[0]+"_"+simpleDateFormat.format(date);
//        }
        String stoken = null;
        String ltoken = null;
        if (fileName.indexOf('.') > -1) {
            stoken = fileName.substring(0, fileName.indexOf('.'));
            ltoken = fileName.substring(fileName.indexOf('.'), fileName.length());
            fileName = stoken + "_" + simpleDateFormat.format(date) + ltoken;
        } else {
            fileName = fileName + "_" + simpleDateFormat.format(date);
        }

        log.debug("#....... file name =" + fileName);
        zettObjectMedia.setReference(fileName);
        zettObjectMedia.setTitle("Stor logo");
        return zettObjectMedia;
    }

    private ModelAndView addZettCompanyData(ModelAndView modelAndView, ExtendedCompanyObject extendedCompanyObject) {
        if (extendedCompanyObject.getObjectId() != null && extendedCompanyObject.getObjectId() > 0) {
            try {

                CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(
                        extendedCompanyObject.getObjectId());
                ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();

                List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();
                ZettObjectMedia[] zettObjectMedias = zettObjectMediaList.toArray(new ZettObjectMedia[0]);

                List<ObjectMedia> objectMediaList = ObjectMediaListTransformer.transform(zettObjectMedias);
                extendedCompanyObject.setObjectMediaList(objectMediaList);

                modelAndView.addObject(COMPANY, extendedCompanyObject);
                modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
            } catch (ServiceException_Exception err) {
                //TODO REVIEW 1/17/13, seem like a serious excepion, can this be handled globally ?
                log.error("can not load company data from this id " + extendedCompanyObject.getObjectId() + " : " + err.getMessage());
            }
        }
        return modelAndView;
    }

    private Contact createBlankContact() {
        Contact contact = new Contact();
        ContactAttribute titleContactAttribute = new ContactAttribute();
        titleContactAttribute.setType(ContactAttributeType.TITLE);
        titleContactAttribute.setTypeValue(ContactAttributeType.TITLE.toInteger());
        titleContactAttribute.setSection("DEFAULT");
        ContactAttribute phoneContactAttribute = new ContactAttribute();
        phoneContactAttribute.setType(ContactAttributeType.PHONE);
        phoneContactAttribute.setTypeValue(ContactAttributeType.PHONE.toInteger());
        phoneContactAttribute.setSection("DEFAULT");
        ContactAttribute mobileContactAttribute = new ContactAttribute();
        mobileContactAttribute.setType(ContactAttributeType.MOBILE);
        mobileContactAttribute.setTypeValue(ContactAttributeType.MOBILE.toInteger());
        mobileContactAttribute.setSection("DEFAULT");
        List<ContactAttribute> contactAttributeList = new ArrayList<ContactAttribute>();
        contactAttributeList.add(titleContactAttribute);
        contactAttributeList.add(phoneContactAttribute);
        contactAttributeList.add(mobileContactAttribute);
        contact.setAttributesRaw(contactAttributeList);
        return contact;
    }

    @RequestMapping(value = "deletecompanycontact.html")
    public ModelAndView deleteContact(Model model,
                                      @RequestParam(value = "contactid", required = false) Integer contactId,
                                      @RequestParam(value = "companyid", required = false) Integer companyId) throws ServiceException_Exception, DatatypeConfigurationException {

        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(companyServicePortType);
        CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);

        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        ExtendedCompanyObjectTransformer extendedCompanyObjectTransformer = new ExtendedCompanyObjectTransformer();
        ExtendedCompanyObject companyObject = extendedCompanyObjectTransformer.transform(zettCompanyObject);

        List<ZettContact> contactList = zettCompanyObject.getContacts();

        if (contactList.size() == 1) {
            modelAndView.addObject("deleteMessage", "showcompany.warning.delete.contact");
            modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
            modelAndView.addObject(COMPANY, companyObject);
            modelAndView.setViewName(SHOW_COMPANY);
            return modelAndView;
        } else {
            zettCompanyObject = companyUpdate.deleteContact(zettCompanyObject.getObjectId(), contactId);
            CompanyObjectResponse newCompanyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
            zettCompanyObject = newCompanyObjectResponse.getZettCompanyObject();
            companyObject = extendedCompanyObjectTransformer.transform(zettCompanyObject);
        }


        modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
        modelAndView.addObject(COMPANY, companyObject);
        modelAndView.setViewName(SHOW_COMPANY);
        return modelAndView;
    }

    @RequestMapping(value = "showMap.html")
    public ModelAndView showMap(Model model,
                                @RequestParam(value = "mapcompanyid", required = false) Integer companyId) throws ServiceException_Exception {

        log.debug("mapcompanyid : " + companyId);
        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(companyServicePortType);
        ExtendedCompanyObjectTransformer extendedCompanyObjectTransformer = new ExtendedCompanyObjectTransformer();

        CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        companyObjectResponse = companyServicePortType.mapLookupCompany(zettCompanyObject);
        zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        companyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
        zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        ExtendedCompanyObject companyObject = extendedCompanyObjectTransformer.transform(zettCompanyObject);

        // there is no mapLookup on asoke yet .  Will uncomment when we have map lookup serivce

        modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
        modelAndView.addObject(COMPANY, companyObject);


        modelAndView.setViewName("redirect:showcompany.html?companyId=" + zettCompanyObject.getObjectId() + "&updateMessage=showcompany.message.map.lookup.pending");

        return modelAndView;
    }


    @RequestMapping(value = "deletecompanymedia.html")
    public ModelAndView deleteMedia(Model model,
                                    @RequestParam(value = "mediaid", required = true) Integer mediaId,
                                    @RequestParam(value = "companymediaid", required = true) Integer companyId) throws ServiceException_Exception, DatatypeConfigurationException {

        ModelAndView modelAndView = new ModelAndView();
        webserviceAuthentication.authentication(companyServicePortType);
        CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        List<ZettObjectMedia> zettObjectMediaList = zettCompanyObject.getMedia();

        //log.debug(" Before Delete Media Size : " + zettObjectMediaList.size());

        if (zettObjectMediaList != null && zettObjectMediaList.size() > 0) {
            ZettObjectMedia zettObjectMedia = null;
            for (ZettObjectMedia media : zettObjectMediaList) {
                if (media.getMediaId() == mediaId) {
                    zettObjectMedia = media;
                }
            }
            if (zettObjectMedia != null) {
                zettObjectMediaList.remove(zettObjectMedia);
                modelAndView.addObject(UPDATE_MESSAGE, "showcompany.message.updated.media");
            }

            //ZettObjectMedia[] arrayOfZettObjectMedia = new ZettObjectMedia[zettObjectMediaList.size()];
            for (ZettObjectMedia zettMedia : zettObjectMediaList) {
                zettCompanyObject.getMedia().add(zettMedia);
            }

            companyObjectResponse = companyServicePortType.saveCompany(zettCompanyObject);
            zettCompanyObject = companyObjectResponse.getZettCompanyObject();

            log.debug("After Delete : " + zettObjectMediaList.size());

        }

        ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObjectTransformer().transform(zettCompanyObject);

        modelAndView.addObject(ZETT_COMPANY_OBJECT, zettCompanyObject);
        modelAndView.addObject(COMPANY, extendedCompanyObject);
        modelAndView.setViewName(SHOW_COMPANY);
        return modelAndView;
    }

    @RequestMapping("deletecompanyattribute.html")
    public String deleteCompanyAttribute(@RequestParam(value = "attributeid", required = true) int attributeId,
                                    @RequestParam(value = "companyid", required = true) int companyId) throws ServiceException_Exception {

        log.debug("delete attribute id = "+attributeId);
        CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        if(zettCompanyObject != null){
            List<ZettObjectAttribute> attributeList = zettCompanyObject.getAttributes();
            if(attributeList != null){
                log.debug("attribute list is not null");
                ZettObjectAttribute targetAttribute = null;
                for(ZettObjectAttribute zettObjectAttribute:attributeList){
                    if(zettObjectAttribute.getAttributeId() == attributeId){
                        log.debug("found this id in attribute list");
                        targetAttribute = zettObjectAttribute;
                    }
                }

                if(targetAttribute!=null){
                    log.debug("deleting attribute id = "+attributeId);
                    zettCompanyObject.getAttributes().remove(targetAttribute);
                    companyServicePortType.saveCompany(zettCompanyObject);
                }
            }
        }

        return "redirect:showcompany.html?companyId=" + companyId + "&updateMessage=showcompany.message.added";
    }


    private ObjectError generateErrorWithException(Exception err) {
        return new ObjectError("*", err.getMessage());
    }

    private ModelAndView createNotFoundModel(Integer companyId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("noresult", "showcompany.message.warning.noresult.id");
        modelAndView.addObject("errorinput", companyId);
        modelAndView.setViewName(SEARCH_COMPANY);
        return modelAndView;

    }

    private ModelAndView createNotFoundModelByTitle(String title) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("noresult", "showcompany.message.warning.noresult.title");
        modelAndView.addObject("errorinput", title);
        modelAndView.setViewName(SEARCH_COMPANY);
        return modelAndView;

    }

    private ModelAndView createComapnyViewResultSearch(ModelAndView model, SearchItemResponse searchItemResponse, String title, String page) {
        if (searchItemResponse != null && searchItemResponse.getSolrResultSize() != 0) {
            model.addObject("zettSearchItems", searchItemResponse.getZettSearchItems());
            int totolResult = (int) searchItemResponse.getSolrResultSize();
            log.debug("Total Result :::" + totolResult);
            model.addObject("totalResult", totolResult);
            PageCalculator pageCalculator = new PageCalculator();
            PageInformation pageInformation = pageCalculator.calculate(page, totolResult);
            model.addObject("currentPage", pageInformation.getCurrentPage());
            model.addObject("totalPage", pageInformation.getTotalPage());
            model.setViewName("showlistcompany");
            return model;

        } else {
            log.debug("####### Title is not found #######");
            return createNotFoundModelByTitle(title);
        }
    }




}