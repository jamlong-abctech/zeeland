package com.abctech.zeeland.controller;

import no.zett.model.base.ObjectAttribute;
import no.zett.xml.attributes.AttributesValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class ZettCommonController {

    private final static Logger log = LoggerFactory.getLogger(ZettCommonController.class);

    @RequestMapping(value = "showzettcommon.html")
    public String showZettCommon(Model model) {

        // Use of deprecated method is OK, the method will not be deprecated in the next release of zett:
        Map<String, ObjectAttribute> map = AttributesValues.getInstance().getObjectattributesmap(); // NOSONAR Will solve itself with the next version of zett.
        List<ObjectAttribute> objectAttrList = new ArrayList<ObjectAttribute>(map.values());

        Collections.sort(objectAttrList, new ObjectAttributeComparator());
        log.debug("ad_attributes_elements : ");
        for (ObjectAttribute objectAttribute : objectAttrList) {
            log.debug(objectAttribute.getName()+ "   " + objectAttribute.getLabel() + "   " + objectAttribute.getType() );
        }

        model.addAttribute("mapAttribute", objectAttrList);
        return "showzettcommon";
    }

}