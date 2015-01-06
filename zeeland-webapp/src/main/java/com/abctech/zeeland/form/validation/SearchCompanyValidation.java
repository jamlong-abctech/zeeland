package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.SearchCompany;
import com.abctech.zeeland.form.util.UtilValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("searchCompanyValidation")
public class SearchCompanyValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchCompany.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchCompany searchCompany = (SearchCompany)target;

        if(searchCompany.getCompanyId() !=null) {
             searchCompany.setCompanyId(searchCompany.getCompanyId().trim());
        }

        if(searchCompany.getTitle()!=null && searchCompany.getCompanyId()!=null){
            if(searchCompany.getCompanyId().isEmpty() && searchCompany.getTitle().isEmpty()) { // Check Input
                errors.reject("validation.emptyField");
            } else if(isNotInputOneField(searchCompany)) { // Check input only one field
                errors.reject("validation.moreOneField");
            } else if(!UtilValidation.isNumeric(searchCompany.getCompanyId()) && !searchCompany.getCompanyId().equals("")) {
                errors.reject("validation.numeric");
            }
        }

    }

    private boolean isNotInputOneField(SearchCompany searchCompany) {
        if(
                !((!searchCompany.getCompanyId().equals("") && searchCompany.getTitle().equals("")) ||
                    (searchCompany.getCompanyId().equals("") && !searchCompany.getTitle().equals("")))
          ) {
            return true;
        }
        return false;
    }

}
