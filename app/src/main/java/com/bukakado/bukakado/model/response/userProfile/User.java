package com.bukakado.bukakado.model.response.userProfile;

/**
 * Created by Jessica Casanova Lim on 5/22/2017.
 */

public class User {
    public int id;
    public String username;
    public String name;
    public String avatar;
    public String level;
    public String lapak_name;
    public String lapak_desc;
    public String lapak_header;
    public String last_login;
    public boolean store_closed;
    public Object schedule_close_store;
    public Object close_date;
    public Object close_reason;
    public Object reopen_date;
    public String joined_at;
    public Rejection rejection;
    public Feedbacks feedbacks;
    public Address address;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLevel() {
        return level;
    }

    public String getLapak_name() {
        return lapak_name;
    }

    public String getLapak_desc() {
        return lapak_desc;
    }

    public String getLapak_header() {
        return lapak_header;
    }

    public String getLast_login() {
        return last_login;
    }

    public boolean isStore_closed() {
        return store_closed;
    }

    public Object getSchedule_close_store() {
        return schedule_close_store;
    }

    public Object getClose_date() {
        return close_date;
    }

    public Object getClose_reason() {
        return close_reason;
    }

    public Object getReopen_date() {
        return reopen_date;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public Rejection getRejection() {
        return rejection;
    }

    public Feedbacks getFeedbacks() {
        return feedbacks;
    }

    public Address getAddress() {
        return address;
    }
}
