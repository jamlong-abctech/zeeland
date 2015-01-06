package com.abctech.zeeland.security;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service(value = "tokenObject")
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Token {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

