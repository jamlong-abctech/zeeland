package com.abctech.zeeland.wsAuthentication.Authenticator;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ZettAuthenticator extends Authenticator {
    private String username, password;

    public ZettAuthenticator(String user, String password) {
        this.username = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }
}

