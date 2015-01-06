package com.abctech.zeeland.form.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator {
    
    public boolean validate(String dateString){
        if(dateString != null && !dateString.isEmpty()){
            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                dateFormat.parse(dateString);
                return true;
            }catch(ParseException err){
                return false;
            }
        }
        return false;
    }
}
