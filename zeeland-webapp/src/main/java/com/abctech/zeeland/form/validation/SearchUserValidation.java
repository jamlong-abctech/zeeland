package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.util.UtilValidation;
import no.zett.model.base.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("searchUserValidation")
public class SearchUserValidation implements Validator {

    //private final static Logger log = LoggerFactory.getLogger(SearchUserValidation.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;
        if(user.getUserId()== null && (user.getEmail() == null || user.getEmail().isEmpty())){
            errors.reject("validation.emptyField");
        }else{
            if(isBothFieldsUsed(user)){
                errors.reject("validation.moreOneField");
            }else{
                if(user.getUserId()!=null && user.getUserId()<1){
                        errors.reject("validation.emptyField");
                }
                if(!user.getEmail().isEmpty() && !UtilValidation.isEmail(user.getEmail())) {
                        errors.reject("validation.email");
                }
            }
        }
    }

    private boolean isBothFieldsUsed(User user) {
        if ( user.getEmail() != null && !user.getEmail().isEmpty() && user.getUserId() != null ) {
            return true;
        }
        return false;
    }

}
