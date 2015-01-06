package com.abctech.zeeland.wsAuthentication;

import com.abctech.zeeland.wsAuthentication.Authenticator.ZettAuthenticator;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.stereotype.Service;

import java.net.Authenticator;

@Service(value = "webserviceAuthentication")
public class WebserviceAuthentication {

    public static final int TIMEOUT = 36000;
    private String username = "ZettServices";
    private String password = "377kankgp027+";

    public void authentication(Object service)  {
        Authenticator.setDefault(new ZettAuthenticator(username, password));
        Client clients = ClientProxy.getClient(service);
        HTTPConduit http = (HTTPConduit) clients.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(TIMEOUT);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
    }
}
