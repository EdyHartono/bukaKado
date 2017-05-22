package com.bukakado.bukakado.model.response.userProfile;

/**
 * Created by Jessica Casanova Lim on 5/22/2017.
 */

public class UserProfileResponse {
    public String status;
    public User user;
    public Object message;

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Object getMessage() {
        return message;
    }
}
