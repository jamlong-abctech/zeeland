package com.abctech.zeeland.service;

import com.abctech.zeeland.enumeration.AttributeGroup;
import com.abctech.zeeland.enumeration.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttributesGenerator {

    private final static Logger log = LoggerFactory.getLogger(AttributesGenerator.class);

    private Map<AttributeGroup,List<AttributeObject>> attributeMap;

    public List<AttributeObject> find(AttributeGroup attributeGroup){
        if(this.attributeMap == null){
            initialize();
        }
        return this.attributeMap.get(attributeGroup);
    }

    public void initialize(){
        try{
            attributeMap = new HashMap<AttributeGroup,List<AttributeObject>>();
            List<AttributeObject> commonAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> autoAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> propertyAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> jobAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> boatAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> companyAttributeList = new ArrayList<AttributeObject>();
            List<AttributeObject> lostAndFoundAttributeList = new ArrayList<AttributeObject>();

            File attributeFile = new File(this.getClass().getClassLoader().getResource("attributes.xml").getPath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(attributeFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Attribute");
            for(int i = 0;i<nList.getLength();i++){
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    String category = element.getAttribute("Category");
                    String name = element.getAttribute("Name");
                    String label = element.getAttribute("Label");
                    String type = element.getAttribute("Type");

                    AttributeObject attributeObject = new AttributeObject();
                    attributeObject.setLabel(label);
                    attributeObject.setName(name);
                    attributeObject.setAttributeType(convertToAttributeGroup(type));

                    if(category.equalsIgnoreCase(AttributeGroup.AUTO.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.AUTO);
                        autoAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.BOAT.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.BOAT);
                        boatAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.COMPANY.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.COMPANY);
                        companyAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.JOB.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.JOB);
                        jobAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.PROPERTY.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.PROPERTY);
                        propertyAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.COMMON.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.COMMON);
                        commonAttributeList.add(attributeObject);
                        autoAttributeList.add(attributeObject);
                        propertyAttributeList.add(attributeObject);
                        jobAttributeList.add(attributeObject);
                        boatAttributeList.add(attributeObject);
                        companyAttributeList.add(attributeObject);
                    }

                    if(category.equalsIgnoreCase(AttributeGroup.LOST_OR_FOUND.toTextValue())){
                        attributeObject.setGroup(AttributeGroup.LOST_OR_FOUND);
                        lostAndFoundAttributeList.add(attributeObject);
                    }
                }
            }

            attributeMap.put(AttributeGroup.AUTO,autoAttributeList);
            attributeMap.put(AttributeGroup.BOAT,boatAttributeList);
            attributeMap.put(AttributeGroup.COMPANY,companyAttributeList);
            attributeMap.put(AttributeGroup.JOB,jobAttributeList);
            attributeMap.put(AttributeGroup.PROPERTY,propertyAttributeList);
            attributeMap.put(AttributeGroup.COMMON,commonAttributeList);
            attributeMap.put(AttributeGroup.LOST_OR_FOUND,lostAndFoundAttributeList);
        }catch (ParserConfigurationException err){
            log.debug(err.getMessage());
        }catch (SAXException err){
            log.debug(err.getMessage());
        }catch (IOException err){
            log.debug(err.getMessage());
        }
    }

    private AttributeType convertToAttributeGroup(String type){
        if(type.equalsIgnoreCase(AttributeType.STRING.toTextValue())){
            return AttributeType.STRING;
        }
        if(type.equalsIgnoreCase(AttributeType.URL.toTextValue())){
            return AttributeType.URL;
        }
        if(type.equalsIgnoreCase(AttributeType.INTEGER.toTextValue())){
            return AttributeType.INTEGER;
        }
        if(type.equalsIgnoreCase(AttributeType.PRICE_NOK.toTextValue())){
            return AttributeType.PRICE_NOK;
        }
        if(type.equalsIgnoreCase(AttributeType.EMAIL.toTextValue())){
            return AttributeType.EMAIL;
        }
        if(type.equalsIgnoreCase(AttributeType.PREFORMATTED_STRING.toTextValue())){
            return AttributeType.PREFORMATTED_STRING;
        }
        if(type.equalsIgnoreCase(AttributeType.LENGTH_FEET.toTextValue())){
            return AttributeType.LENGTH_FEET;
        }
        if(type.equalsIgnoreCase(AttributeType.AREA_SIZE.toTextValue())){
            return AttributeType.AREA_SIZE;
        }
        if(type.equalsIgnoreCase(AttributeType.DOUBLE.toTextValue())){
            return AttributeType.DOUBLE;
        }
        if(type.equalsIgnoreCase(AttributeType.PRICE_EUR.toTextValue())){
            return AttributeType.PRICE_EUR;
        }
        if(type.equalsIgnoreCase(AttributeType.PRICE_USD.toTextValue())){
            return AttributeType.PRICE_USD;
        }

        return null;
    }

}
