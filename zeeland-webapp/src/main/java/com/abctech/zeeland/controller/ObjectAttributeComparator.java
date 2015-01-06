package com.abctech.zeeland.controller;

import no.zett.model.base.ObjectAttribute;

import java.io.Serializable;
import java.util.Comparator;

public class ObjectAttributeComparator implements Comparator<ObjectAttribute>, Serializable {

    @Override
    public int compare(ObjectAttribute obj1, ObjectAttribute obj2) {
        return obj1.getName().compareTo(obj2.getName());
    }
}
