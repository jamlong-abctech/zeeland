package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.SearchCompany;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

public class SearchCompanyValidationTest {

    private static final Logger log = LoggerFactory.getLogger(SearchAdValidationTest.class);

    @Test
    public void validationTest() {
        SearchCompanyValidation searchCompanyValidation = new SearchCompanyValidation();
        //Check Input
        SearchCompany searchCompany = new SearchCompany();
        searchCompany.setCompanyId("");
        searchCompany.setTitle("");
        BindingResult result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(searchCompanyValidation, searchCompany, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
        // Check valid number
        searchCompany.setCompanyId("abc");
        searchCompany.setTitle("");
        result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(searchCompanyValidation, searchCompany, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check valid number =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
        // Check input only one field
        searchCompany.setCompanyId("123");
        searchCompany.setTitle("abc");
        result = new BeanPropertyBindingResult(searchCompany, "searchCompany");
        ValidationUtils.invokeValidator(searchCompanyValidation, searchCompany, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input only one field =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;
    }

}
