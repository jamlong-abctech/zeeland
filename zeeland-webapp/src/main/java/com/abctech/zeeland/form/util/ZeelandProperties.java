package com.abctech.zeeland.form.util;

import no.api.properties.api.ApiPropertiesManager;
import no.api.properties.api.ApiProperty;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class ZeelandProperties implements InitializingBean {

    private ApiPropertiesManager apiPropertiesManager;
	private Properties zeelandProperties;

   
    public void setApiPropertiesManager(ApiPropertiesManager apiPropertiesManager) {
        this.apiPropertiesManager = apiPropertiesManager;
    }
    
	public Properties getZeelandProperties() {
		return zeelandProperties;
	}

	public void setZeelandProperties(Properties zeelandProperties) {
		this.zeelandProperties = zeelandProperties;
	}
	
    @Override
    public void afterPropertiesSet() throws IOException {
        zeelandProperties = new Properties();
        zeelandProperties.load(ZeelandProperties.class.getResourceAsStream("/zeeland.properties"));
    }

    public String getSvindelloggPassword() {
        return fromApiOrZeelandProperties("zeeland.svindellogg.password");
    }

    public String getSvindelloggUrl() {
        return fromApiOrZeelandProperties("zeeland.svindellogg.url");
    }
    
    public String getBookingAdminUrl(){
        return fromApiOrZeelandProperties("zeeland.bookingadmin.url");
    }

    public String getPropertyUrl(){
        return fromApiOrZeelandProperties("zeeland.properties.url")  ;
    }

    public  String getZettUrl(){
        return fromApiOrZeelandProperties("zeeland.zett.url");
    }

    public String getTransitionURL() {
        ApiProperty apiProperty = apiPropertiesManager.getGlobalProperty("hydra.to.transition.callback.url");
        String fullUrl = apiProperty.getValue();
        return fullUrl.substring(0, fullUrl.lastIndexOf('/'));
    }
    
    public String getZettServicesURL() {
        return fromApiOrZeelandProperties("zeeland.zservices.url");
    }

    private String fromApiOrZeelandProperties(String propertyName) {
        ApiProperty apiProperty = apiPropertiesManager.getGlobalProperty(propertyName);
        if(apiProperty != null){
            return apiProperty.getValue();
        }
        return zeelandProperties.getProperty(propertyName);
    }

    public String getZettLink(){
        return fromApiOrZeelandProperties("zeeland.zett.link");
    }

    public String getZservicesurlpaht(){
        return  fromApiOrZeelandProperties("zeeland.zservices.url.path")  ;
    }

    public String getObscuraImageVersion() {
        return fromApiOrZeelandProperties("zeeland.obscura.image.version");
    }

    public String getObscuraImageLogoVersion() {
        return fromApiOrZeelandProperties("zeeland.obscura.image.logo.version");
    }
    
}
