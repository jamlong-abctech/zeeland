package com.abctech.zeeland.controller;

import junit.framework.Assert;
import no.zett.model.base.ObjectAttribute;
import no.zett.model.enums.ObjectAttributeType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectAttributeComparatorTest {
    @Test
    public void testCompare() throws Exception {
        ObjectAttribute a = new ObjectAttribute("aaaaa", ObjectAttributeType.INTEGER);
        ObjectAttribute b = new ObjectAttribute("bbbbb", ObjectAttributeType.PRICE_NOK);
        ObjectAttribute c = new ObjectAttribute("ccccc", ObjectAttributeType.STRING);
        List<ObjectAttribute> list = new ArrayList<ObjectAttribute>();
        list.add(c);
        list.add(a);
        list.add(b);

        Collections.sort(list, new ObjectAttributeComparator());
        Assert.assertEquals("aaaaa", list.remove(0).getName());
        Assert.assertEquals("bbbbb", list.remove(0).getName());
        Assert.assertEquals("ccccc", list.remove(0).getName());

        System.out.println("sorted list by Name:");
        for (ObjectAttribute objectAttribute : list) {
            System.out.println(objectAttribute.getName() + "   " + objectAttribute.getType());
        }
    }
}
