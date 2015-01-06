package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.SearchAd;
import com.abctech.zeeland.form.util.UtilValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("searchAdValidation")
public class SearchAdValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchAd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchAd searchAd = (SearchAd)target;

        // Trim String
        if(searchAd.getAdId()!=null){
            searchAd.setAdId(searchAd.getAdId().trim());
        }
        if(searchAd.getExtRef()!=null){
            searchAd.setExtRef(searchAd.getExtRef().trim());
        }
        if(searchAd.getClientEmail()!=null){
            searchAd.setClientEmail(searchAd.getClientEmail().trim());
        }
        if(searchAd.getSearchKeyword()!=null){
            searchAd.setSearchKeyword(searchAd.getSearchKeyword().trim());
        }

        if(searchAd.getPrintAdId()!=null){
            searchAd.setPrintAdId(searchAd.getPrintAdId().trim());
        }
        if(searchAd.getOrderId() !=null){
            searchAd.setOrderId(searchAd.getOrderId().trim());
        }

        if(searchAd.getAdId()!=null && searchAd.getExtRef()!=null && searchAd.getClientEmail()!=null&&searchAd.getSearchKeyword()!=null&&searchAd.getPrintAdId()!=null && searchAd.getOrderId()!=null){
            if(searchAd.getAdId().isEmpty() && searchAd.getExtRef().isEmpty() && searchAd.getClientEmail().isEmpty() && searchAd.getSearchKeyword().isEmpty() && searchAd.getPrintAdId().isEmpty() && searchAd.getPrintAdId().isEmpty() && searchAd.getOrderId().isEmpty()){
                errors.reject("validation.emptyField");
            }else if (isNotInputOneField(searchAd)) { // Check input only one field
                errors.reject("validation.moreOneField");
            }else if(!UtilValidation.isNumeric(searchAd.getAdId()) && !searchAd.getAdId().equals("")) { // Check valid number
                errors.reject("validation.numeric");

            }else if(!UtilValidation.isEmail(searchAd.getClientEmail()) && !searchAd.getClientEmail().equals("")) { // Check valid email
                errors.reject("validation.email");
            }else if(!UtilValidation.isNumeric(searchAd.getPrintAdId()) && !searchAd.getPrintAdId().equals("")){  // Check valid number
                errors.reject("validation.numeric");
            }else if(!UtilValidation.isNumeric(searchAd.getOrderId()) && !searchAd.getOrderId().equals("")){  // Check valid number
                errors.reject("validation.numeric");
            }
        }
    }

    private boolean isNotInputOneField(SearchAd searchAd) {
        if(
                !(  (!searchAd.getAdId().equals("") && searchAd.getExtRef().equals("") && searchAd.getClientEmail().equals("") && searchAd.getSearchKeyword().equals("") && searchAd.getPrintAdId().equals("")) && searchAd.getOrderId().equals("")||
                    (searchAd.getAdId().equals("") && !searchAd.getExtRef().equals("") && searchAd.getClientEmail().equals("") && searchAd.getSearchKeyword().equals("") && searchAd.getPrintAdId().equals("")) && searchAd.getOrderId().equals("")||
                    (searchAd.getAdId().equals("") && searchAd.getExtRef().equals("") && !searchAd.getClientEmail().equals("")&& searchAd.getSearchKeyword().equals("") && searchAd.getPrintAdId().equals("")) && searchAd.getOrderId().equals("") ||
                    (searchAd.getAdId().equals("") && searchAd.getExtRef().equals("") && searchAd.getClientEmail().equals("")&& !searchAd.getSearchKeyword().equals("") && searchAd.getPrintAdId().equals("")) && searchAd.getOrderId().equals("") ||
                    (searchAd.getAdId().equals("") && searchAd.getExtRef().equals("") && searchAd.getClientEmail().equals("")&& searchAd.getSearchKeyword().equals("") && !searchAd.getPrintAdId().equals("") && searchAd.getOrderId().equals("") ||
                    (searchAd.getAdId().equals("") && searchAd.getExtRef().equals("") && searchAd.getClientEmail().equals("")&& searchAd.getSearchKeyword().equals("") && searchAd.getPrintAdId().equals("")&&!searchAd.getOrderId().equals("")))
                )

          ) {
            return true;
        }
        return false;
    }

}