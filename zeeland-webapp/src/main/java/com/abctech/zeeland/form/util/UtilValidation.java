package com.abctech.zeeland.form.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilValidation {

    private UtilValidation() {

    }

    public static boolean isNumeric(String number) {
        Pattern pNumber = Pattern.compile("^[0-9]*")   ;
        return isMatch(number, pNumber);
    }

    public static boolean isEmail(String email) {
        Pattern pEmail = Pattern.compile(".+@.+\\.[a-z]+");
        return isMatch(email, pEmail);
    }

    private static boolean isMatch(String value, Pattern pattern) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isAllNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /*public static boolean  isNumericAndNoSpace(String number){
        Pattern pNumber = Pattern.compile("^\d+$")  ;
        return isMatch(number,pNumber);
    }*/


}
