package com.abctech.zeeland.adminPing;

import no.api.hydra.admin.HydraAdminPingQuery;

import javax.servlet.ServletContext;


public class PingZeeland  implements HydraAdminPingQuery {
    public static final int YELLOW_VALUE = 90;
    public static final int RED_VALUE = 100;
    private ServletContext servletContext;

    @Override
    public boolean canWebappBeUsed() {
        return true;
    }

    @Override
    public String getAboutInformation() {
        return "NOTICE: Not doing anything useful yet";
    }

    @Override
    public int yellowWaterMark() {
        return YELLOW_VALUE;
    }

    @Override
    public int upPercentage() {
        return RED_VALUE;
    }

    @Override
    public String getReason() {
        return null;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }
}
