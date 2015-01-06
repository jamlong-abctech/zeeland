package com.abctech.zeeland.service;

import com.abctech.zeeland.enumeration.AttributeGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:zeeland-client-test.xml", "classpath:/META-INF/apiproperties-applicationContext.xml"})
public class AttributesGeneratorTest {

    @Autowired
    private AttributesGenerator attributesGenerator;

    @Test
    public void commonAttributeGenerationTesting(){
        List<AttributeObject> attributeObjectList = attributesGenerator.find(AttributeGroup.COMMON);
        Assert.assertEquals("common attribute should be ", 20, attributeObjectList.size());
    }

    @Test
    public void autoAttributeGenerationTesting(){
        List<AttributeObject> attributeObjectList = attributesGenerator.find(AttributeGroup.AUTO);
        Assert.assertEquals("auto attribute should be (auto 38 + common 20)", 58, attributeObjectList.size());
    }

    @Test
    public void propertyAttributeGenerationTesting(){
        List<AttributeObject> attributeObjectList = attributesGenerator.find(AttributeGroup.PROPERTY);
        Assert.assertEquals("property attribute should be (property 25 + common 20)", 45, attributeObjectList.size());
    }

    @Test
    public void boatAttributeGenerationTesting(){
        List<AttributeObject> attributeObjectList = attributesGenerator.find(AttributeGroup.BOAT);
        Assert.assertEquals("boat attribute should be (boat 7 + common 20)", 27, attributeObjectList.size());
    }
}
