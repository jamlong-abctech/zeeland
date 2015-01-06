package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.data.ExtendedFraudBadWord;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SaveFraudBadWordValidation implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return ExtendedFraudBadWord.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtendedFraudBadWord extendedFraudBadWord = (ExtendedFraudBadWord)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newBadWord", "validation.emptyField");
    }
}
