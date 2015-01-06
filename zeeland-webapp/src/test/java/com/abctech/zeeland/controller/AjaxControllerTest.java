package com.abctech.zeeland.controller;

import com.abctech.zeeland.enumeration.AttributeGroup;
import com.abctech.zeeland.enumeration.AttributeType;
import com.abctech.zeeland.service.AttributeObject;
import com.abctech.zeeland.service.AttributesGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:zeeland-client-test.xml", "classpath:/META-INF/apiproperties-applicationContext.xml"})
public class AjaxControllerTest {

    private final static Logger log = LoggerFactory.getLogger(AjaxControllerTest.class);

    @Autowired
    private AjaxController ajaxController;

    @Autowired
    private AttributesGenerator attributeGenerator;

    private String generateResponse(AttributeObject attributeObject){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        stringBuilder.append("\"type\":\"");
        stringBuilder.append(attributeObject.getAttributeType().toTextValue());
        stringBuilder.append("\",\n");
        stringBuilder.append("\"label\":\"");
        stringBuilder.append(attributeObject.getLabel());
        stringBuilder.append("\"\n}");
        return stringBuilder.toString();
    }

    @Test
    public void autoAttributeAjaxResponseTest()throws Exception{
        List<AttributeObject> attributeObjectList = attributeGenerator.find(AttributeGroup.AUTO);
        for(AttributeObject attributeObject:attributeObjectList){
            MockHttpServletResponse response = new MockHttpServletResponse();
            ajaxController.generateCategoryAttribute("Motor",attributeObject.getName(),response);
            String result = response.getContentAsString();
            Assert.assertEquals(result, generateResponse(attributeObject));
        }

    }

    @Test
    public void propertyAttributeAjaxResponseTest()throws Exception{
        List<AttributeObject> attributeObjectList = attributeGenerator.find(AttributeGroup.PROPERTY);
        for(AttributeObject attributeObject:attributeObjectList){
            MockHttpServletResponse response = new MockHttpServletResponse();
            ajaxController.generateCategoryAttribute("Eiendom",attributeObject.getName(),response);
            String result = response.getContentAsString();
            Assert.assertEquals(result, generateResponse(attributeObject));
        }

    }

    @Test
    public void jobAttributeAjaxResponseTest()throws Exception{
        List<AttributeObject> attributeObjectList = attributeGenerator.find(AttributeGroup.JOB);
        for(AttributeObject attributeObject:attributeObjectList){
            MockHttpServletResponse response = new MockHttpServletResponse();
            ajaxController.generateCategoryAttribute("Stilling",attributeObject.getName(),response);
            String result = response.getContentAsString();
            Assert.assertEquals(result, generateResponse(attributeObject));
        }

    }

    @Test
    public void boatAttributeAjaxResponseTest()throws Exception{
        List<AttributeObject> attributeObjectList = attributeGenerator.find(AttributeGroup.BOAT);
        for(AttributeObject attributeObject:attributeObjectList){
            MockHttpServletResponse response = new MockHttpServletResponse();
            ajaxController.generateCategoryAttribute("Båt",attributeObject.getName(),response);
            String result = response.getContentAsString();
            Assert.assertEquals(result, generateResponse(attributeObject));
        }

    }

    @Test
    public void otherAttributeAjaxResponseTest()throws Exception{
        List<AttributeObject> attributeObjectList = attributeGenerator.find(AttributeGroup.COMMON);
        for(AttributeObject attributeObject:attributeObjectList){
            MockHttpServletResponse response = new MockHttpServletResponse();
            ajaxController.generateCategoryAttribute("temp1234",attributeObject.getName(),response);
            String result = response.getContentAsString();
            Assert.assertEquals(result, generateResponse(attributeObject));
        }

    }

}
