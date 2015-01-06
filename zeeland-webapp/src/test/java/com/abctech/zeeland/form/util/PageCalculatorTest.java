package com.abctech.zeeland.form.util;

import org.junit.Assert;
import org.junit.Test;

public class PageCalculatorTest {

    @Test
    public void testCalculate(){
        PageCalculator pageCalculator = new PageCalculator();
        PageInformation pageInformation = pageCalculator.calculate("1",15);
        Assert.assertEquals("item less than 20 page count should be 1",1,pageInformation.getTotalPage());

        PageInformation pageInformation2 = pageCalculator.calculate("3",45);
        Assert.assertEquals("45 items should be 3 pages",3,pageInformation2.getTotalPage());
        Assert.assertEquals("current page should be 3 ",3,pageInformation2.getCurrentPage());
        Assert.assertEquals("page 3 should start with record #40 ",40,pageInformation2.getStartRecord());
        Assert.assertEquals("page 3 should end with record #45",45,pageInformation2.getEndRecord());

        PageInformation pageInformation3 = pageCalculator.calculate("1",20);
        Assert.assertEquals("20 items should be 1 pages",1,pageInformation3.getTotalPage());

        PageInformation pageInformation4 = pageCalculator.calculate("1",40);
        Assert.assertEquals("40 items should be 1 pages",2,pageInformation4.getTotalPage());

    }
}
