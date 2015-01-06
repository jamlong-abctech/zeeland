package com.abctech.zeeland.controller;

import com.abctech.mockland.runner.MocklandWebService;
import com.abctech.zeeland.form.SearchAd;
import com.abctech.zeeland.form.SearchCompany;
import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.form.validation.SearchAdValidation;
import com.abctech.zeeland.form.validation.SearchCompanyValidation;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.enums.ObjectAttributeType;
import no.zett.service.facade.AdService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.xml.attributes.AttributesValues;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/apiproperties-applicationContext.xml","classpath:junit/zeeland-appcontext.xml", "classpath:zeeland-client-test.xml"})
public class ZeelandControllerTest {

    private final Logger log = LoggerFactory.getLogger(ZeelandControllerTest.class);

    @Autowired
    private ZeelandController zeelandController;

    @Autowired
    private AdController adController;

    @Autowired
    private AdService adServicePortType;

    @Autowired
    private ZettCommonController zettCommonController;

    @Autowired
    private CompanyController companyController ;

    private static MocklandWebService mocklandWebService;
    private static MockHttpServletRequest mockRequestPost;
    private static MockHttpServletRequest mockRequestGet;
    private static MockHttpServletResponse response;
    private static HandlerAdapter handlerAdapter;


    @BeforeClass
    public static void before() throws Exception {
        mocklandWebService = MocklandWebService.createMocklandWebService();
        mocklandWebService.start();
        response = new MockHttpServletResponse();
        handlerAdapter = new AnnotationMethodHandlerAdapter();
        System.setProperty("mockland.mvnws", mocklandWebService.getBaseURI());
//        System.setProperty("mockland.ws","http://localhost:9033/mockland-webservice");
//        System.setProperty("mockland.ws","http://services.zett.no");
    }

    @AfterClass
    public static void after() throws Exception {
        // make some test changes (demo)
        mocklandWebService.stop();
    }

    @Ignore
    @Test
    public void indexTest() throws IOException, ServiceException_Exception {
        Assert.assertEquals("home", zeelandController.home(null, null, null));

    }

    @Ignore
    @Test
    public void searchAdTest() throws Exception, ServiceException_Exception {
        SearchAd searchAd = new SearchAd();
        // post Ad Id
        String adId = "2144437";
        searchAd.setAdId(adId);
        searchAd.setExtRef("");
        searchAd.setClientEmail("");
        Integer printId = 123 ;

        BindingResult result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(new SearchAdValidation(), searchAd, result);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("adId", adId);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setParameter("printAdId" , "1234");

        ModelAndView modelAndView1 = handlerAdapter.handle(request, response, adController);
        assertViewName(modelAndView1,"redirect:searchad" + modelAndView1.getViewName());
        // post Ext Ref
        String extRef = "ZB124900";
        searchAd.setAdId("");
        searchAd.setExtRef(extRef);
        searchAd.setClientEmail("");
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(new SearchAdValidation(), searchAd, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("extRef", extRef);
        ModelAndView  modelAndView = handlerAdapter.handle(request,response,adController) ;
        assertViewName(modelAndView, "redirect:showlistad" + modelAndView.getViewName());

        // Post Email
        String clientEmail = "eralfred@online.no";
        searchAd.setAdId("");
        searchAd.setExtRef("");
        searchAd.setClientEmail(clientEmail);
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(new SearchAdValidation(), searchAd, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("clientEmail", clientEmail);
        ModelAndView listAd = handlerAdapter.handle(request, response, adController);
        assertViewName(listAd,"redirect:searchad" + modelAndView1.getViewName());

        // No value
        clientEmail = "mac@mac.com";
        searchAd.setAdId("");
        searchAd.setExtRef("");
        searchAd.setClientEmail(clientEmail);
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(new SearchAdValidation(), searchAd, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("clientEmail", clientEmail);
        ModelAndView searchByEmail = handlerAdapter.handle(request, response, adController);
        assertViewName(searchByEmail,"redirect:searchad" + modelAndView1.getViewName());

    }

    @Ignore
    @Test
    public void showSingleAdTest() throws ServiceException_Exception, IOException, ParseException {
        //POST
        mockRequestPost = new MockHttpServletRequest("POST", "");
        mockRequestPost.setParameter("adId", "2144437");
        Assert.assertEquals("showad", adController.showSingleAd(new ExtendedModelMap(), new ExtendedAdObject(),null,null, null, mockRequestPost));
        mockRequestPost.setParameter("adId", "123456");
        Assert.assertEquals("searchad", adController.showSingleAd(new ExtendedModelMap(), new ExtendedAdObject(),null,null, null, mockRequestPost));

        //GET
        mockRequestGet = new MockHttpServletRequest("GET", "");
        Assert.assertEquals("showad", adController.showSingleAd(new ExtendedModelMap(), new ExtendedAdObject(), 2144437,null, null,mockRequestGet));
        // No  value
        Assert.assertEquals("searchad", adController.showSingleAd(new ExtendedModelMap(), new ExtendedAdObject(), 123456,null,null,mockRequestGet));
        // No parameter
        Assert.assertEquals("searchad", adController.showSingleAd(new ExtendedModelMap(), new ExtendedAdObject(), null,null,null, mockRequestGet));
    }

    /*
    @Ignore
    @Test
    public void searchUserTest() throws IOException, ServiceException_Exception {
        SearchUser searchUser = new SearchUser();
        //Post User Id
        String userId = "219756";
        searchUser.setUserId(userId);
        searchUser.setEmail("");
        BindingResult result = new BeanPropertyBindingResult(searchUser, "searchUser");
        ValidationUtils.invokeValidator(new SearchUserValidation(), searchUser, result);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("userId",userId);
        //Assert.assertEquals("searchuser", zeelandController.searchUser(new ExtendedModelMap(), searchUser, result, mockHttpServletRequest, new MockHttpServletResponse()));
        Assert.assertEquals("forward:/showuser.html", zeelandController.searchUser(new ExtendedModelMap(), searchUser, result, mockHttpServletRequest, new MockHttpServletResponse()));
        //Post Email
        String email = "an-romb@online.no";
        searchUser.setUserId("");
        searchUser.setEmail(email);
        result = new BeanPropertyBindingResult(searchUser, "searchUser");
        ValidationUtils.invokeValidator(new SearchUserValidation(), searchUser, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("email",email);
        Assert.assertEquals("searchuser", zeelandController.searchUser(new ExtendedModelMap(), searchUser, result, mockHttpServletRequest, new MockHttpServletResponse()));
        // No value
        email = "mac@mac.com";
        searchUser.setUserId("");
        searchUser.setEmail(email);
        result = new BeanPropertyBindingResult(searchUser, "searchUser");
        ValidationUtils.invokeValidator(new SearchUserValidation(), searchUser, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("email",email);
        Assert.assertEquals("noresult", zeelandController.searchUser(new ExtendedModelMap(), searchUser, result, mockHttpServletRequest, new MockHttpServletResponse()));
    }*/

//    @Ignore
//    @Test
//    public void showSingleUserTest() throws ServiceException_Exception {
//        //POST
//        mockRequestPost = new MockHttpServletRequest("POST", "");
//        mockRequestPost.setParameter("userId", "219756");
//        Assert.assertEquals("showuser", zeelandController.showSingleUser(new ExtendedModelMap(), null, mockRequestPost));
//        mockRequestPost.setParameter("userId", "123456");
//        Assert.assertEquals("noresult", zeelandController.showSingleUser(new ExtendedModelMap(), null, mockRequestPost));
//
//        //GET
//        mockRequestGet = new MockHttpServletRequest("GET", "");
//        Assert.assertEquals("showuser", zeelandController.showSingleUser(new ExtendedModelMap(), 219756, mockRequestGet));
//        // No value
//        Assert.assertEquals("noresult", zeelandController.showSingleUser(new ExtendedModelMap(), 123456, mockRequestGet));
//        // No parameter
//        Assert.assertEquals("noresult", zeelandController.showSingleUser(new ExtendedModelMap(), null, mockRequestGet));
//    }

    @Ignore
    @Test
    public void searchCompanyTest() throws IOException, ServiceException_Exception, DatatypeConfigurationException {
        SearchCompany searchCompany = new SearchCompany();
        // Post Company Id
        String companyId = "10021";
        searchCompany.setCompanyId(companyId);
        searchCompany.setTitle("");
        String telefon = "1234567";
        String mobile = "081751111";
        String titleContact = "title";
        BindingResult result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(new SearchCompanyValidation(), searchCompany, result);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("companyId", companyId);
        //Assert.assertEquals("searchcompany", zeelandController.searchCompany(new ExtendedModelMap(), searchCompany, result, mockHttpServletRequest, new MockHttpServletResponse()));
        Assert.assertEquals("forward:/showcompany.html", zeelandController.searchCompany(new ExtendedModelMap(), searchCompany, result, mockHttpServletRequest, new MockHttpServletResponse()));
        // Post title
        String title = "Helgeland Arbeiderblad";
        searchCompany.setCompanyId("");
        searchCompany.setTitle(title);


        result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(new SearchCompanyValidation(), searchCompany, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("title", title);
        //Assert.assertEquals("showlistcompany", zeelandController.searchCompany(new ExtendedModelMap(), searchCompany, result, mockHttpServletRequest, new MockHttpServletResponse()));
        // No value
        title = "Abc";
        searchCompany.setCompanyId("");
        searchCompany.setTitle(title);

        result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(new SearchCompanyValidation(), searchCompany, result);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setParameter("title", title);


        //Assert.assertEquals("noresult", zeelandController.searchCompany(new ExtendedModelMap(), searchCompany, result, mockHttpServletRequest, new MockHttpServletResponse()));
    }

    @Ignore
    @Test
    public void showCompanyTest() throws ServiceException_Exception {

        ZettCompanyObject zettCompanyObject = new ZettCompanyObject();
        BindingResult result = new BeanPropertyBindingResult(zettCompanyObject, "zettCompanyObject");
        //POST
        mockRequestPost = new MockHttpServletRequest("POST", "");
        mockRequestPost.setParameter("companyId", "10021");
        Assert.assertEquals("showcompany", zeelandController.showCompany(new ExtendedModelMap(), null, null, mockRequestPost));
        mockRequestPost.setParameter("companyId", "123456");
        Assert.assertEquals("noresult", zeelandController.showCompany(new ExtendedModelMap(), null, null, mockRequestPost));

        //GET
        mockRequestGet = new MockHttpServletRequest("GET", "");
        Assert.assertEquals("showcompany", zeelandController.showCompany(new ExtendedModelMap(), 10021, null, mockRequestGet));
        // No value
        Assert.assertEquals("noresult", zeelandController.showCompany(new ExtendedModelMap(), 123456, null, mockRequestGet));
        // No parameter
        Assert.assertEquals("noresult", zeelandController.showCompany(new ExtendedModelMap(), null, null, mockRequestGet));
    }

    @Ignore
    @Test
    public void deleteContactTest() throws Exception {
     mockRequestPost = new MockHttpServletRequest() ;
        mockRequestPost.setRequestURI("/deletecompanycontact.html");
        mockRequestPost.setParameter("companyid" , "10021");
        mockRequestPost.setParameter("contactid" , "34666638");
        final ModelAndView modelAndView = handlerAdapter.handle(mockRequestPost,response,companyController);
        Map model = modelAndView.getModel();
        junit.framework.Assert.assertNotNull("Expect to get the model" + model);

    }

    @Ignore
    @Test
    public void showZettCommonTest() throws Exception {
        //test "basecolor"
        Assert.assertEquals("basecolor", AttributesValues.getInstance().getClonedObjectAttribute("basecolor").getName());
        Assert.assertEquals(ObjectAttributeType.STRING, AttributesValues.getInstance().getClonedObjectAttribute("basecolor").getType());
        Assert.assertEquals("Basisfarge", AttributesValues.getInstance().getClonedObjectAttribute("basecolor").getLabel());

        Assert.assertEquals("showzettcommon", zettCommonController.showZettCommon(new ExtendedModelMap()));
    }

    @Ignore
    @Test
    public void readAttributeTest() {
        Map<String, ObjectAttribute> map = AttributesValues.getInstance().getObjectattributesmap();
        for (ObjectAttribute item : map.values()) {
            System.out.println("" + item.getName() + "  " + item.getLabel() + "  " + item.getType());
        }
    }


}



