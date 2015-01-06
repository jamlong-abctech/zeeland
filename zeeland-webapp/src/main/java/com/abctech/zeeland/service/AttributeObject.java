package com.abctech.zeeland.service;

import com.abctech.zeeland.enumeration.AttributeGroup;
import com.abctech.zeeland.enumeration.AttributeType;

public class AttributeObject {

    private AttributeGroup group;
    private String name;
    private String label;
    private AttributeType attributeType;

    public AttributeGroup getGroup() {
        return group;
    }

    public void setGroup(AttributeGroup group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
