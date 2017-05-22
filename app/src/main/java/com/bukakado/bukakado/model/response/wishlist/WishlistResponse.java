package com.bukakado.bukakado.model.response.wishlist;

import java.util.List;

/**
 * Created by Jessica Casanova Lim on 5/21/2017.
 */

public class WishlistResponse {
    public String status;
    public List<Product> products;
    public Object message;

    public String getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Object getMessage() {
        return message;
    }
}
