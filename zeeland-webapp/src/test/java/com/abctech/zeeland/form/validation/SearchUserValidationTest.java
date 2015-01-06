package com.abctech.zeeland.form.validation;

import no.zett.model.base.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

public class SearchUserValidationTest {

    private static final Logger log = LoggerFactory.getLogger(SearchAdValidationTest.class);

    @Test
    public void validationTest() {

        SearchUserValidation searchUserValidation = new SearchUserValidation();
        // Chech Input
        User user = new User();
        user.setUserId(0);
        user.setEmail("");
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        ValidationUtils.invokeValidator(searchUserValidation, user, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));

        // Check valid email
        user.setUserId(null);
        user.setEmail("mac");
        result = new BeanPropertyBindingResult(user, "user");
        ValidationUtils.invokeValidator(searchUserValidation, user, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check valid email =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;

        // Check input only one field
        user.setUserId(123);
        user.setEmail("mac@mac.com");
        result = new BeanPropertyBindingResult(user, "searchAd");
        ValidationUtils.invokeValidator(searchUserValidation, user, result);
        Assert.assertTrue(result.hasErrors());
        log.debug("===== Check input only one field =====");
        log.debug("Has error = " + result.hasErrors());
        log.debug("Error counts = " + Integer.toString(result.getErrorCount()));
        result = null;

    }


    @Test
    public void testFailureWhenEmptyObject() {
        SearchUserValidation searchUserValidation = new SearchUserValidation();
        User user = new User();
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        ValidationUtils.invokeValidator(searchUserValidation, user, result);
        Assert.assertTrue(result.hasErrors());
    }

    @Test
    public void testFailureWhenNoInput() {
        SearchUserValidation searchUserValidation = new SearchUserValidation();
        User user = new User();
        user.setEmail("");
        user.setUserId(Integer.valueOf(0));
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        ValidationUtils.invokeValidator(searchUserValidation, user, result);
        Assert.assertTrue(result.hasErrors());
        boolean found = lookForKey(result);
        Assert.assertTrue("Expecting to find specific key among the error messages.", found);
    }

    private boolean lookForKey(BindingResult result) {
        boolean found = false;
        for ( ObjectError error : result.getAllErrors()) {
            if ( error.getCode().equals("validation.emptyField")) {
                found = true;
            }
        }
        return found;
    }

}
