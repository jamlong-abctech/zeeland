package com.abctech.zeeland.controller;

import com.abctech.mockland.runner.MocklandWebService;
import no.zett.service.facade.AdService;
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
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:junit/zeeland-appcontext.xml", "classpath:zeeland-client-test.xml", "classpath:/META-INF/apiproperties-applicationContext.xml"})


public class UserControllerTest {

    private final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

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

       @Autowired
       private UserController userController ;

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
        System.setProperty("mockland.ws", mocklandWebService.getBaseURI());
//        System.setProperty("mockland.ws","http://localhost:9033/mockland-webservice");
//        System.setProperty("mockland.ws","http://services.zett.no");
    }

    @AfterClass
    public static void after() throws Exception {
        mocklandWebService.stop();
    }

   @Ignore
    @Test
    public void updateAdownerTest() throws Exception {
        mockRequestPost = new MockHttpServletRequest() ;
        mockRequestPost.setRequestURI("/createaddowner.html");
        mockRequestPost.setParameter("adId","1439378" );
        mockRequestPost.setParameter("useremail" , "terjerin@online.no");
        final ModelAndView modelAndView = handlerAdapter.handle(mockRequestPost,response, userController);
        Map model = modelAndView.getModel() ;
        log.debug(">>>" + model);
        Assert.assertNotNull("Expect to get model" + model);
    }



}