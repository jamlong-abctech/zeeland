package com.abctech.zeeland.form.validation;


import com.abctech.zeeland.form.SearchAd;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AdBatchDeleteValidation implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return SearchAd.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SearchAd searchAd = (SearchAd)o;
        if(searchAd.getAdIds() == null || "".equals(searchAd.getAdIds())) {
            errors.reject("delad.invalid.input");
        } else {
            searchAd.getAdIds().replaceAll("\\n", "\\s");
            String[] adIds = searchAd.getAdIds().split("\\s+");
            for (String id : adIds) {
                try {
                    Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    errors.reject("delad.invalid.input");
                    break;
                }
            }
        }
    }
}
