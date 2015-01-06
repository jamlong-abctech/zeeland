package com.abctech.zeeland.form.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class XMLGregorianCalendarGenerator {

    private final static Logger log = LoggerFactory.getLogger(XMLGregorianCalendarGenerator.class);

    private XMLGregorianCalendar createXMLGregorianCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        try{
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        }catch (DatatypeConfigurationException e){
            log.debug(e.getMessage());
            return null;
        }
    }

    public XMLGregorianCalendar generateXMLGregorianCalendar(){
        DateTime dateTime = new DateTime();
        return createXMLGregorianCalendar(dateTime.toDate());
    }

    public XMLGregorianCalendar generateXMLGregorianCalendar(Date date){
        return createXMLGregorianCalendar(date);
    }
}
