package com.abctech.zeeland.form.validation;


import com.abctech.zeeland.form.data.ExtendedCompanyObject;
import com.abctech.zeeland.form.util.UtilValidation;
import no.zett.model.base.Contact;
import no.zett.model.base.ObjectAttribute;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Service("saveCompanyValidation")
public class SaveCompanyValidation implements Validator {

    //private final static Logger log = LoggerFactory.getLogger(SaveCompanyValidation.class);

    @Override
    public boolean supports(Class<?> cls) {
        return ExtendedCompanyObject.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "validation.emptyField.company.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.primaryAddress", "validation.emptyField.company.primaryAddress");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postCode", "validation.emptyField.company.postCode");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postLocation", "validation.emptyField.company.postLocation");

        ExtendedCompanyObject extendedCompanyObject = (ExtendedCompanyObject) target;

        //if have new contact, add to contact list before start validation
        if(extendedCompanyObject.getExtendedContact().getName()!=null && extendedCompanyObject.getExtendedContact().getEmail()!=null){
            if(!extendedCompanyObject.getExtendedContact().getName().isEmpty() && !extendedCompanyObject.getExtendedContact().getEmail().isEmpty()){
                if(extendedCompanyObject.getContacts() == null){
                    List<Contact> contactList = new ArrayList<Contact>();
                    extendedCompanyObject.setContacts(contactList);
                }
                extendedCompanyObject.getContacts().add(extendedCompanyObject.getExtendedContact());
            }
        }

        if (extendedCompanyObject.getContacts() != null && extendedCompanyObject.getContacts().size() > 0) {
            boolean checkContact = false;
            for (int i = 0; i < extendedCompanyObject.getContacts().size(); i++) {

                if (extendedCompanyObject.getContacts().get(i).getName() != null && !extendedCompanyObject.getContacts().get(i).getName().isEmpty()) {
                    checkContact = true;
                    ValidationUtils.rejectIfEmpty(errors, "contacts[" + i + "].email", "validation.emptyField.company.contact.email");
                    if (extendedCompanyObject.getContacts().get(i).getEmail() != null
                            && !extendedCompanyObject.getContacts().get(i).getEmail().isEmpty()
                            && !UtilValidation.isEmail(extendedCompanyObject.getContacts().get(i).getEmail())) {
                        errors.rejectValue("contacts[" + i + "].email", "validation.email");
                    }
                    for (ObjectAttribute objectAttribute : extendedCompanyObject.getAttributes()) {
                        if (objectAttribute.getName().equals("homepage")
                                && !objectAttribute.getValue().equals("")
                                && !objectAttribute.getValue().startsWith("http://")) {
                            errors.rejectValue("attributes[0].value", "validation.invalid.url");
                        }
                    }
                }
            }

            if (!checkContact) {
                 errors.reject("validation.company.contact.noEmpty");
            }
        } else {
            errors.reject("validation.company.contact.noEmpty");
        }

        ObjectAttribute extendedObjectAttribute = extendedCompanyObject.getExtendedAttribute();
        if(extendedObjectAttribute.getName()!=null && !extendedObjectAttribute.getName().trim().equals("")){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.value","validation.emptyField.extendedAttributeValue");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.type","validation.emptyField.extendedAttributeType");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"extendedAttribute.label","validation.emptyField.extendedAttributeLabel");
        }

    }

}
