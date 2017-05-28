package com.bukakado.bukakado.model.response.userAddress;

/**
 * Created by Jessica Casanova Lim on 5/28/2017.
 */

public class AddressAttributes {
    public int id;
    public String address;
    public String area;
    public String city;
    public String province;
    public String post_code;

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPost_code() {
        return post_code;
    }
}
