package com.abctech.zeeland.parser;

import com.abctech.zeeland.controller.ObjectAttributeComparator;
import no.zett.model.base.ObjectAttribute;
import no.zett.xml.attributes.AttributesValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class AttributeValueParser {
    private static final Logger log = LoggerFactory.getLogger(AttributeValueParser.class);

    public List<ObjectAttribute> retrieveAttributeList(){
        Map<String, ObjectAttribute> map = AttributesValues.getInstance().getObjectattributesmap(); // NOSONAR Will solve itself with the next version of zett.
        List<ObjectAttribute> objectAttrList = new ArrayList<ObjectAttribute>(map.values());
        Collections.sort(objectAttrList, new ObjectAttributeComparator());

        return objectAttrList;
    }

}
