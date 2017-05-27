package com.bukakado.bukakado.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by edy.h on 5/12/2017.
 */

public class User {
    private String userId;
    private String userName;
    private String photoUrl;
    private String email;
    private String country;
    private String sex;
    private String bukalapakUserId;
    private String bukalapakUserToken;

    public User() {

    }

    public User(String userId, String userName, String photoUrl, String email, String country, String sex) {
        this.userId = userId;
        this.userName = userName;
        this.photoUrl = photoUrl;
        this.email = email;
        this.country = country;
        this.sex = sex;
    }

    public User(FirebaseUser user) {
        this(
                user.getUid(),
                user.getDisplayName(),
                user.getPhotoUrl().toString(),
                user.getEmail(),
                "",""
        );
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBukalapakUserId() {
        return bukalapakUserId;
    }

    public void setBukalapakUserId(String bukalapakUserId) {
        this.bukalapakUserId = bukalapakUserId;
    }

    public String getBukalapakUserToken() {
        return bukalapakUserToken;
    }

    public void setBukalapakUserToken(String bukalapakUserToken) {
        this.bukalapakUserToken = bukalapakUserToken;
    }
}
