package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.SearchAd;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

public class SearchAdValidationTest {

    private static final Logger log = LoggerFactory.getLogger(SearchAdValidationTest.class);

    @Test
    public void validationTest() {
        SearchAdValidation searchAdValidation = new SearchAdValidation();
        // Check input
        SearchAd searchAd = new SearchAd();
        searchAd.setAdId("");
        searchAd.setExtRef("");
        searchAd.setClientEmail("");
        searchAd.setSearchKeyword("");
        searchAd.setPrintAdId("");
        searchAd.setOrderId("");
        BindingResult result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(searchAdValidation, searchAd, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
        // Check valid number
        searchAd.setAdId("        aa");
        searchAd.setExtRef("");
        searchAd.setClientEmail("");
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(searchAdValidation, searchAd, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check valid number =====");
        log.debug("Has error = " + result.hasErrors()  + " | " + result.getAllErrors().get(0).getCode());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
         // Check valid number with space




        // Check valid email
        searchAd.setAdId("");
        searchAd.setExtRef("");
        searchAd.setClientEmail("mac");
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(searchAdValidation, searchAd, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check valid email =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
        // Check input only one field
        searchAd.setAdId("123");
        searchAd.setExtRef("");
        searchAd.setClientEmail("mac@mac.com");
        result = new BeanPropertyBindingResult(searchAd, "searchAd");
        ValidationUtils.invokeValidator(searchAdValidation, searchAd, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input only one field =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;

    }
}
