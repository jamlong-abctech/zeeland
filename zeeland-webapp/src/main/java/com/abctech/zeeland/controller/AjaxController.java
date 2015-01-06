package com.abctech.zeeland.controller;

import com.abctech.zeeland.enumeration.AttributeGroup;
import com.abctech.zeeland.enumeration.AttributeType;
import com.abctech.zeeland.form.util.CategoryBuilder;
import com.abctech.zeeland.parser.AttributeValueParser;
import com.abctech.zeeland.service.AttributeObject;
import com.abctech.zeeland.service.AttributesGenerator;
import no.zett.model.base.AdObjectCategory;
import no.zett.model.base.ObjectAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AjaxController {

    private final static Logger log = LoggerFactory.getLogger(AjaxController.class);

    @Autowired
    private CategoryBuilder categoryBuilder;

    @Autowired
    private AttributesGenerator attributesGenerator;

    @Autowired
    private AttributeValueParser parser;

    private final static String CLOSETAG = ")'>";

    @RequestMapping(value = "getchildcategory.html",method = RequestMethod.GET)
    public ModelAndView getChildCategory(@RequestParam(value = "catId", required = true)Integer catId){
        ModelAndView modelAndView = new ModelAndView();

        List<AdObjectCategory> adObjectCategoryList;
        StringBuffer stringBuffer = new StringBuffer();
        AdObjectCategory category;
        if(catId != null && catId > 0){
            adObjectCategoryList = categoryBuilder.getChildCategory(catId);
            category = categoryBuilder.getCategoryById(catId);
            if(category!=null){
                stringBuffer.append("<li id='cat").append(category.getCategoryId()).append("'>")
                .append("<a onclick='hideChildCategory(").append(category.getCategoryId()).append(CLOSETAG)
                .append("<img src='images/minus-icon.gif' class='icon' />")
                .append(category.getName())
                .append("</a>");
                if(adObjectCategoryList.size()>0){
                    stringBuffer.append("\n<ul>");
                    for(AdObjectCategory adObjectCategory:adObjectCategoryList){
                        stringBuffer.append("\n\t<li id='cat").append(adObjectCategory.getCategoryId()).append("'>");
                        if(categoryBuilder.getChildCategory(adObjectCategory.getCategoryId()).size()>0){
                            stringBuffer.append("<a onclick='getChildCategory(").append(adObjectCategory.getCategoryId()).append(CLOSETAG);
                            stringBuffer.append("<img src='images/plus-icon.gif' class='icon' />");
                        }else{
                            stringBuffer.append("<a onclick='selectCategory(").append(adObjectCategory.getCategoryId()).append(CLOSETAG);
                            stringBuffer.append("<img src='images/blank-icon.gif' class='icon' />");
                        }
                        stringBuffer.append(adObjectCategory.getName()).append("</a>");
                        stringBuffer.append("</li>");
                    }
                    stringBuffer.append("\n</ul>");
                }
                stringBuffer.append("\n</li>");
            }
        }

        modelAndView.addObject("result", stringBuffer.toString());
        modelAndView.setViewName("ajaxresponse");
        return modelAndView;
    }

    @RequestMapping(value = "getchildelementcategory.html",method = RequestMethod.GET)
    public ModelAndView getChildElementCategory(@RequestParam(value = "catId", required = true)Integer catId){
        ModelAndView modelAndView = new ModelAndView();
        AdObjectCategory thisCat = categoryBuilder.getCategoryById(catId);
        String[] level = thisCat.getFullname().split("\\/");
        int levelId = 1;
        if(level.length>1){
            levelId = level.length;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Integer.toString(levelId));
        stringBuffer.append("|");
        List<AdObjectCategory> adObjectCategoryList = categoryBuilder.getChildCategory(catId);
        if(adObjectCategoryList.size()>0){
            stringBuffer.append("\n<select id=\"catlv");
            stringBuffer.append(Integer.toString(levelId));
            stringBuffer.append("\"");
            stringBuffer.append("name=\"category_list\"");
            stringBuffer.append(" onChange=\"getChildElementCategory(this.value)\">");
            stringBuffer.append("\n\t<option value=\"0\"> --- Please Select --- </option>");
            for(AdObjectCategory adObjectCategory:adObjectCategoryList){
                stringBuffer.append("\n\t<option value=\"");
                stringBuffer.append(adObjectCategory.getCategoryId());
                stringBuffer.append("\">");
                stringBuffer.append(adObjectCategory.getName());
                stringBuffer.append("</option>");
            }
            stringBuffer.append("\n</select>");
        }
        modelAndView.addObject("result",stringBuffer.toString());
        modelAndView.setViewName("ajaxresponse");
        return modelAndView;
    }

    @RequestMapping(value = "hidechildcategory.html",method = RequestMethod.GET)
    public ModelAndView hideChildCategory(@RequestParam(value = "catId", required = true)Integer catId){
        ModelAndView modelAndView = new ModelAndView();
        AdObjectCategory category = categoryBuilder.getCategoryById(catId);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<li id='cat").append(category.getCategoryId()).append("'>")
            .append("<a onclick='getChildCategory(").append(category.getCategoryId()).append(CLOSETAG)
            .append("<img src='images/plus-icon.gif' class='icon' />")
            .append(category.getName())
            .append("</a></li>");
        modelAndView.addObject("result",stringBuffer.toString());
        modelAndView.setViewName("ajaxresponse");
        return modelAndView;
    }

    @RequestMapping(value = "getfullcategory.html",method = RequestMethod.GET)
    public ModelAndView getFullCategoryName(@RequestParam(value = "catId", required = true)Integer catId){
        ModelAndView modelAndView = new ModelAndView();
        if(catId != null && catId>0){
            AdObjectCategory adObjectCategory = categoryBuilder.getCategoryById(catId);
            modelAndView.addObject("result",adObjectCategory.getFullname());
        }
        modelAndView.setViewName("ajaxresponse");
        return modelAndView;
    }

    @RequestMapping(value = "getcategoryattribute.html",method = RequestMethod.GET)
    public void generateCategoryAttribute(@RequestParam(value = "cat", required = true)String category,
                                          @RequestParam(value = "name", required = true)String attributeName,
                                          HttpServletResponse response){
        AttributeGroup attributeGroup = AttributeGroup.COMMON;

        if(category.equalsIgnoreCase("Motor")){
            attributeGroup = AttributeGroup.AUTO;
        }if(category.equalsIgnoreCase("Eiendom")){
            attributeGroup = AttributeGroup.PROPERTY;
        }if(category.equalsIgnoreCase("Stilling")){
            attributeGroup = AttributeGroup.JOB;
        }if(category.equalsIgnoreCase("Båt")){
            attributeGroup = AttributeGroup.BOAT;
        }

        StringBuilder json = new StringBuilder();
        json.append("{");
        List<AttributeObject> attributeObjectList = attributesGenerator.find(attributeGroup);
        AttributeObject attributeObject = null;
        for(AttributeObject attribute:attributeObjectList){
            if(attributeName.equalsIgnoreCase(attribute.getName())){
                attributeObject = attribute;
            }
        }

        if(attributeObject != null){
            json.append("\n");
            json.append("\"").append("type").append("\"");
            json.append(":");
            json.append("\"").append(attributeObject.getAttributeType().toTextValue()).append("\"");
            json.append(",");
            json.append("\n");
            json.append("\"").append("label").append("\"");
            json.append(":");
            json.append("\"").append(attributeObject.getLabel()).append("\"");
            json.append("\n");
            json.append("}");
            log.debug("attribute: {}", json.toString());
            try {
                response.setContentType("application/json");
                response.getWriter().print(json.toString());
                response.getWriter().flush();
            } catch (IOException err) {
                log.debug("can not write the response");
            }
        }

    }

    @RequestMapping(value = "getattribute.html",method = RequestMethod.GET)
    public void generateAttribute(@RequestParam(value = "name", required = true)String attributeName,
                                          HttpServletResponse response){

        StringBuilder json = new StringBuilder();
        json.append("{");
        List<ObjectAttribute> objectAttributeList = parser.retrieveAttributeList();
        ObjectAttribute Objectattribute = null;
        for(ObjectAttribute attribute: objectAttributeList){
            if(attributeName.equalsIgnoreCase(attribute.getName())){
                Objectattribute = attribute;
            }
        }

        if(Objectattribute != null){
            json.append("\n");
            json.append("\"").append("type").append("\"");
            json.append(":");
            json.append("\"").append(AttributeType.fromNumberValue(Objectattribute.getTypeValue() - 1).toString()).append("\"");
            json.append(",");
            json.append("\n");
            json.append("\"").append("label").append("\"");
            json.append(":");
            json.append("\"").append(Objectattribute.getLabel()).append("\"");
            json.append("\n");
            json.append("}");
            try {
                response.setContentType("application/json");
                response.getWriter().print(json.toString());
                response.getWriter().flush();
            } catch (IOException err) {
                log.error("Can not write the response: {}", err);
            }
        }

    }

}
