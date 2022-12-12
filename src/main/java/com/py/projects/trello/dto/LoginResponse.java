package com.py.projects.trello.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private final String jwtToken;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}