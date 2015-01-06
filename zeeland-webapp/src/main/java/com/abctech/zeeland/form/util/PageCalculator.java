package com.abctech.zeeland.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageCalculator {

    private final static Logger log = LoggerFactory.getLogger(PageCalculator.class);
    private int recordPerPage = 20;

    public PageInformation calculate(String page,int totalRecord){
        PageInformation pageInformation = new PageInformation();

        int currentPage = 1;
        if(page!=null){
            try{
                currentPage = Integer.valueOf(page);
                if(currentPage < 1){
                    currentPage = 1;
                }
            }catch (NumberFormatException err){
                log.warn("can not convert this value to string : " + page);
            }
        }

        Double totalPage = Math.ceil((double)totalRecord / (double) recordPerPage);
        if(currentPage > totalPage){
            currentPage = totalPage.intValue();
        }

        int startRecord = ((currentPage-1) * 20);
        int endRecord = startRecord + 20;
        if(endRecord > totalRecord){
            endRecord = totalRecord;
        }

        pageInformation.setCurrentPage(currentPage);
        pageInformation.setTotalPage(totalPage.intValue());
        pageInformation.setStartRecord(startRecord);
        pageInformation.setEndRecord(endRecord);

        return pageInformation;
    }


}
