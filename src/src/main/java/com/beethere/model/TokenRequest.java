package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenRequest {
    private String token;

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");

    public TokenRequest() {
        APPLICATION_LOGGER.debug("Constructing Default TokenRequest");
    }

    public TokenRequest(String token) {
        APPLICATION_LOGGER.debug("Constructing TokenRequest");
        this.token = token;
    }

    public String getToken() {
        APPLICATION_LOGGER.debug("Getting token");
        return token;
    }

    public void setToken(String token) {
        APPLICATION_LOGGER.debug("Setting token");
        this.token = token;
    }
}

