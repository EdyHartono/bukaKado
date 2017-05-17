package com.bukakado.bukakado.model.request;

/**
 * Created by edy.h on 5/11/2017.
 */

public class NewChatRequest {
    private String message;
    private String from;
    private String to;

    public NewChatRequest() {

    }

    public NewChatRequest(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
