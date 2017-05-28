package com.bukakado.bukakado.model.response.userAddress;

/**
 * Created by Jessica Casanova Lim on 5/28/2017.
 */

public class UserAddress {
    public int id;
    public boolean primary;
    public String title;
    public String name;
    public String phone;
    public AddressAttributes address_attributes;

    public int getId() {
        return id;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public AddressAttributes getAddress_attributes() {
        return address_attributes;
    }
}
