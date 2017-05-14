package com.bukakado.bukakado.model.response;

/**
 * Created by Jessica Casanova Lim on 5/14/2017.
 */

public class BukalapakLoginResponse {
    private String status;
    private String message;
    private Object data;
    private int user_id;
    private String user_name;
    private Boolean confirmed;
    private String token;
    private String email;
    private String confirmed_phone;
    private String omnikey;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmed_phone() {
        return confirmed_phone;
    }

    public String getOmnikey() {
        return omnikey;
    }
}
