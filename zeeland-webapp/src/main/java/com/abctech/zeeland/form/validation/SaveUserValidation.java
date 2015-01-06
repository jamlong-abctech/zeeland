package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.service.facade.CompanyObjectResponse;
import no.zett.service.facade.CompanyService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.ZettCompanyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Service("saveUserValidation")
public class SaveUserValidation implements Validator {

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private CompanyService companyServicePortType;

    @Override
    public boolean supports(Class<?> cls) {
        return ExtendedUser.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","validation.emptyField.user.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","validation.emptyField.user.email");

        ExtendedUser extendedUser = (ExtendedUser)target;

        //check password in add new user case (new user , id = null)
        if(extendedUser.getUserId()==null || extendedUser.getUserId() == 0){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","validation.emptyField.user.password");
        }

        if(extendedUser.getCompany()!=null && extendedUser.getCompany().getObjectId()!=null
            && !checkValidCompanyId(extendedUser.getCompany().getObjectId())){
                 errors.reject("validation.company.notfound");
        }

        if(extendedUser.getCompanyAccess()!=null && !extendedUser.getCompanyAccess().isEmpty()){
            String[] companyAccess = extendedUser.getCompanyAccess().split(",");
            for(String company:companyAccess){
                try{
                    Integer companyId = Integer.valueOf(company);
                    if(!checkValidCompanyId(companyId)){
                        errors.reject("validation.company.notfound");
                    }
                }catch (NumberFormatException err){
                    errors.reject("validation.company.invalid");
                }
            }
        }

    }

    private boolean checkValidCompanyId(Integer companyId) {
        if(companyId != null){
            try{
                webserviceAuthentication.authentication(companyServicePortType);
                CompanyObjectResponse companyObjectResponse = companyServicePortType.loadCompany(companyId);
                if(companyObjectResponse.getZettCompanyObject()!=null && companyObjectResponse.getZettCompanyObject() != null){
                    ZettCompanyObject zettCompanyObject = companyObjectResponse.getZettCompanyObject();
                    if(zettCompanyObject.getObjectId() == companyId){
                        return true;
                    }
                }
            }catch(ServiceException_Exception err){

            }
        }

        return false;
    }
}
