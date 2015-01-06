package com.abctech.zeeland.controller;

import com.abctech.zeeland.form.SearchAd;
import com.abctech.zeeland.form.SearchCompany;
import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.form.validation.SearchCompanyValidation;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.model.enums.ObjectStatus;
import no.zett.model.enums.PackageDealType;
import no.zett.service.facade.CompanyObjectResponse;
import no.zett.service.facade.CompanyService;
import no.zett.service.facade.SearchItemResponse;
import no.zett.service.facade.SearchItemService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettSearchItem;
import org.joda.time.DateTime;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ZeelandController {

    private static final String POST_METHOD = "POST";
    private static final String ERROR_LIST = "errorList";
    private static final String COMPANY_ID = "companyId";
    private static final String NORESULT = "noresult";
//    private final static Logger log = LoggerFactory.getLogger(ZeelandController.class);

    @Autowired
    @Qualifier(value = "searchCompanyValidation")
    private SearchCompanyValidation searchCompanyValidation;

    private String timeFormat = "EEE MMM dd kk:mm:ss 'CEST' y";
    
    @Autowired
    private SearchItemService searchItemServicePortType;
    
    @Autowired
    private CompanyService companyServicePortType;

    // TODO Application context here is a bit strange. We should have access to application context in other ways.
    //private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:zeeland-client.xml");


    private final static Logger log = LoggerFactory.getLogger(ZeelandController.class);

    @Autowired
    @Qualifier(value = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @RequestMapping(value = "index.html")
    public String index(@RequestParam(required = false) String token) {
        return "redirect:home.html";
    }

    //@RequestParam(required = false) String token
    @RequestMapping(value = "home.html")
    public String home(@ModelAttribute("searchAd") SearchAd searchAd,
                        @ModelAttribute("extendedUser") ExtendedUser extendedUser,
                        @ModelAttribute("searchCompany") SearchCompany searchCompany) {
        return "home";
    }

    @RequestMapping(value = "searchcompany1.html")
    public String searchCompany(Model model,
                                @ModelAttribute("searchCompany") SearchCompany searchCompany,
                                BindingResult result,
                                HttpServletRequest request,
                                HttpServletResponse response) throws ServiceException_Exception, IOException {


        // Validation form
        if (request.getMethod().equals(POST_METHOD)) { // Submit search
            searchCompanyValidation.validate(searchCompany, result);

            if (!result.hasErrors()) { // No error
                if (!searchCompany.getCompanyId().equals("")) { // Search by company id
                    //response.sendRedirect("showcompany.html?companyId=" + searchCompany.getCompanyId());
                    return "forward:/showcompany.html";
                } else { // Search by title
                    // SearchItemService
                    //SearchItemService searchItemServicePortType = (SearchItemService) context.getBean("SearchItemService");
                    webserviceAuthentication.authentication(searchItemServicePortType);
                    SearchItemResponse searchItemResponse = searchItemServicePortType.titleSearchCompany(searchCompany.getTitle(), null, 0);
                    if (searchItemResponse == null || searchItemResponse.getZettSearchItems() == null) {
                        return getLinkBackSearchPage(model, "searchcompany.html");
                    }
                    ZettSearchItem[] arrayOfZettSearchItem = new ZettSearchItem[searchItemResponse.getZettSearchItems().size()];
                    arrayOfZettSearchItem = searchItemResponse.getZettSearchItems().toArray(arrayOfZettSearchItem);
                    List<ZettSearchItem> zettSearchItems = Arrays.asList(arrayOfZettSearchItem);
                    model.addAttribute("zettSearchItems", zettSearchItems);
                    return "showlistcompany";
                }
            }
        }

        return "searchcompany";
    }

    @RequestMapping(value = "showcompany1.html")
    public String showCompany(Model model,
                              @RequestParam(value = COMPANY_ID, required = false) Integer companyId,
                              @RequestParam(value = "status", required = false) String status,
                              HttpServletRequest request)
            throws ServiceException_Exception{




        Integer showCompanyId = 0;

        if (request.getMethod().equals(POST_METHOD)) {
            showCompanyId = Integer.valueOf(request.getParameter(COMPANY_ID));

        } else {//if (request.getMethod().equals("GET")) {
            if (companyId != null) {
                showCompanyId = companyId;
            }
            if (request.getParameter(ERROR_LIST) != null) {
                model.addAttribute(ERROR_LIST, request.getParameter(ERROR_LIST));
            }
        }

        //CompanyService companyServicePortType = (CompanyService)context.getBean("clientCompanyServicePortType");
        webserviceAuthentication.authentication(companyServicePortType);
        CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(showCompanyId);
        if (companyObjectResponse == null || companyObjectResponse.getZettCompanyObject() == null) { // No search result

            return getLinkBackSearchPage(model, "searchcompany.html");
        }

        ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
        model.addAttribute("zettCompanyObject", zettCompanyObject);
        DateTime createTime = new DateTime(zettCompanyObject.getCreatedTime());
        model.addAttribute("createTime", createTime.toString(timeFormat));
        //DateTime modifiedTime = new DateTime(zettCompanyObject.getModifiedTime().getYear(), zettCompanyObject.getModifiedTime().getMonth(), zettCompanyObject.getModifiedTime().getDay(), zettCompanyObject.getModifiedTime().getHour(), zettCompanyObject.getModifiedTime().getMinute(), zettCompanyObject.getModifiedTime().getSecond(), (zettCompanyObject.getModifiedTime().getMillisecond()<0 ? 0 : zettCompanyObject.getModifiedTime().getMillisecond()) );
        //model.addAttribute("modifiedTime", modifiedTime.toString(timeFormat));

        model.addAttribute("packageDealType", PackageDealType.fromInteger(zettCompanyObject.getPackageDeal().getPackageDealtype()));
        model.addAttribute("status", ObjectStatus.fromInteger(zettCompanyObject.getStatus()));

        List<ObjectStatus> objectStatusListList = new ArrayList<ObjectStatus>();
        for (ObjectStatus objectStatus : ObjectStatus.values()) {
            objectStatusListList.add(objectStatus);
        }

        model.addAttribute("objectStatusList", objectStatusListList);
        //CompanyObject companyObject = new CompanyObjectTransformer().transform(zettCompanyObject);


        //model.addAttribute("company", companyObject);
        return "showcompany";
    }

       public String getLinkBackSearchPage(Model model , String page) {
        model.addAttribute(NORESULT, page);
        return NORESULT;
    }

    public String getLinkBackSearchPageByExternalRef(Model model,String extRef) {
        model.addAttribute(NORESULT, "showad.message.warning.noresult.externalref" );
        model.addAttribute("errorinput" , extRef);


        return "searchad";
    }

     public String getLinkBackSearchPageByEmail(Model model , String email) {

        model.addAttribute(NORESULT, "showad.message.warning.noresult.email");
        model.addAttribute("errorinput" , email);
        return "searchad";
    }

     public String getLinkBackSearchPageByID(Model model , Integer showAdId) {
        model.addAttribute(NORESULT, "showad.message.warning.noresult.id");
        model.addAttribute("errorinput" , showAdId);
        return "searchad";
    }

    //getLinkBackSearchPageBySearch
    public String getLinkBackSearchPageBySearch(Model model , String keyword) {
        model.addAttribute(NORESULT, "showad.message.warning.noresult.id");
        model.addAttribute("errorinput" , keyword);
        return "searchad";
    }

    public String getLinkBackSearchPageByFilterSearch(Model model , String keyword , String status) {

        model.addAttribute(NORESULT, "showad.message.warning.noresult.keyword");
        model.addAttribute("errorinput" , keyword + " "  + status);
        return "searchad";
    }
}
