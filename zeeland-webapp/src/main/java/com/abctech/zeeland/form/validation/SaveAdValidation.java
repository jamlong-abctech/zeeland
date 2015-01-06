package com.abctech.zeeland.form.validation;

import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.form.util.DateFormatValidator;
import no.zett.model.base.ObjectAttribute;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service("saveAdValidation")
public class SaveAdValidation implements Validator {

//    private final static Logger log = LoggerFactory.getLogger(SaveAdValidation.class);

    @Override
    public boolean supports(Class<?> cls) {
        return ExtendedAdObject.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ExtendedAdObject extendedAdObject = (ExtendedAdObject)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "validation.emptyField");

        ObjectAttribute extendedObjectAttribute = extendedAdObject.getExtendedAttribute();

        DateFormatValidator dateFormatValidator = new DateFormatValidator();
        if(extendedAdObject.getPublishFromTimeString() != null && !extendedAdObject.getPublishFromTimeString().isEmpty()){
            if(!dateFormatValidator.validate(extendedAdObject.getPublishFromTimeString())){
                errors.rejectValue("publishFromTimeString", "validation.date.wrong.format");
            }
        }

        if(extendedAdObject.getPublishToTimeString() != null && !extendedAdObject.getPublishToTimeString().isEmpty()){
            if(!dateFormatValidator.validate(extendedAdObject.getPublishToTimeString())){
                errors.rejectValue("PublishToTimeString", "validation.date.wrong.format");
            }
        }

        if(extendedObjectAttribute.getName()!=null && !extendedObjectAttribute.getName().trim().equals("")){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.value","validation.emptyField.extendedAttributeValue");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.type","validation.emptyField.extendedAttributeType");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.label","validation.emptyField.extendedAttributeLabel");
        }
    }
}
