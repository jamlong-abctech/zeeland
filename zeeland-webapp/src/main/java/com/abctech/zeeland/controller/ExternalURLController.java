package com.abctech.zeeland.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExternalURLController {

    private static Logger log = LoggerFactory.getLogger(ExternalURLController.class);

    @RequestMapping(value = "fraudlogdisplay.html")
    public void redirectToFraudLog(){
        log.debug("try to go to Svindellog");
    }

    @RequestMapping(value = "bookingadmin.html")
    public void redirectToBookingAdmin(){
        log.debug("try to go to BookingAdmin");
    }
}
