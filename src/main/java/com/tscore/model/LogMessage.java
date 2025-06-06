package com.tscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogMessage {

    @JsonProperty("message")
    public String message;

    public LogMessage(String message) {
        this.message = message;
    }
}
