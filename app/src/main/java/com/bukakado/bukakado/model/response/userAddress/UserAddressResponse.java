package com.bukakado.bukakado.model.response.userAddress;

import java.util.List;

/**
 * Created by Jessica Casanova Lim on 5/28/2017.
 */

public class UserAddressResponse {
    public String status;
    public String message;
    public List<UserAddress> user_addresses;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<UserAddress> getUser_addresses() {
        return user_addresses;
    }
}
