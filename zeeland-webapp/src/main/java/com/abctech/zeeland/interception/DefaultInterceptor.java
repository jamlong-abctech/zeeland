package com.abctech.zeeland.interception;

import com.abctech.zeeland.form.util.ZeelandProperties;
import com.abctech.zeeland.security.PrincipalSecurityComponent;
import no.api.hydra.transport.HydraPrincipal;
import no.zett.crypto.MD5Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class DefaultInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(DefaultInterceptor.class);
	private static final String TOKEN = "token";

    @Autowired
	private PrincipalSecurityComponent security;

    @Autowired
    private ZeelandProperties zeelandProperties;

	// Spring 3.0 does not support exclusion yet,
	private List<String> excludeList = null;

    @Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception { // NOSONAR this is actually an override

        String token = request.getParameter(TOKEN);
		String requestUri = request.getRequestURI();
		log.debug("RequestUri: " + requestUri);

		HydraPrincipal p = null;
		if (!excludeHandle(requestUri)) {
			if (token==null && request.getSession().getAttribute(TOKEN)!=null) {
				token = (String) request.getSession().getAttribute(TOKEN);
			}
			p = security.extractPrincipalFromToken(token);
			if (p == null) {
				response.sendRedirect("accessdenied.html");
				return false;
			}else{
                if(requestUri.equals("/zeeland/fraudlogdisplay.html")){
                    response.sendRedirect(redirectToFraudLog(p));
                }else if(requestUri.equals("/zeeland/bookingadmin.html")){
                    response.sendRedirect(redirectToBookingAdmin(p));
                }
            }

			request.getSession().setAttribute(TOKEN, token);
			log.debug("Token is: "+token);
		}
		return super.preHandle(request, response, handler);
	}

	private boolean excludeHandle(String requestUri) {
		if (excludeList!=null) {
			for (String exclude : excludeList) {
				if (requestUri.endsWith(exclude)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
	throws Exception { //NOSONAR this is actually an override
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception { //NOSONAR this is actually an override
        // TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

    private String redirectToFraudLog(HydraPrincipal p){
        return generateAdminURL(p,zeelandProperties.getSvindelloggUrl());
    }

    private String redirectToBookingAdmin(HydraPrincipal p){
        //http://booking.zett.no/vis/rubrikk/onlinebooking/adminBookingOrder.action?timestamp=1324373261186&passphrase=2471C68E5AE65B83DD0EA312A54F4575
        return generateAdminURL(p,zeelandProperties.getBookingAdminUrl());
    }
    
    private String generateAdminURL(HydraPrincipal p,String url){
        Long timestamp = new Date().getTime();
        String passPhrase = MD5Generator.encryptToHex(p.getName() + zeelandProperties.getSvindelloggPassword() + timestamp);
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(url);
        urlBuffer.append("?user=");
        urlBuffer.append(p.getName());
        urlBuffer.append("&timestamp=");
        urlBuffer.append(timestamp);
        urlBuffer.append("&passphrase=");
        urlBuffer.append(passPhrase);
        return urlBuffer.toString();
    }

	public List<String> getExcludeList() {
		return excludeList;
	}

	public void setExcludeList(List<String> excludeList) {
		this.excludeList = excludeList;
	}

}
