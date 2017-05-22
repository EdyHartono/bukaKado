package com.bukakado.bukakado.model.response.wishlist;

import java.util.List;

/**
 * Created by Jessica Casanova Lim on 5/21/2017.
 */

public class Product {
    public DealInfo deal_info;
    public String deal_request_state;
    public int price;
    public List<String> courier;
    public String seller_username;
    public String seller_name;
    public int seller_id;
    public String seller_avatar;
    public String seller_level;
    public String seller_level_badge_url;
    public String seller_delivery_time;
    public int seller_positive_feedback;
    public int seller_negative_feedback;
    public String seller_term_condition;
    public Object seller_alert;
    public Boolean for_sale;
    public List<Object> state_description;
    public String last_relist_at;
    public Specs specs;
    public Boolean force_insurance;
    public List<Object> free_shipping_coverage;
    public String id;
    public String url;
    public String name;
    public Boolean active;
    public String city;
    public String province;
    public int weight;
    public List<Integer> image_ids;
    public List<String> images;
    public List<String> small_images;
    public String desc;
    public String condition;
    public Boolean nego;
    public int stock;
    public Object minimum_negotiable;
    public int view_count;
    public int sold_count;
    public int waiting_payment;
    public Boolean favorited;
    public String created_at;
    public List<Object> product_sin;
    public int category_id;
    public String category;
    public List<String> category_structure;
    public int interest_count;

    public DealInfo getDeal_info() {
        return deal_info;
    }

    public String getDeal_request_state() {
        return deal_request_state;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getCourier() {
        return courier;
    }

    public String getSeller_username() {
        return seller_username;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public String getSeller_avatar() {
        return seller_avatar;
    }

    public String getSeller_level() {
        return seller_level;
    }

    public String getSeller_level_badge_url() {
        return seller_level_badge_url;
    }

    public String getSeller_delivery_time() {
        return seller_delivery_time;
    }

    public int getSeller_positive_feedback() {
        return seller_positive_feedback;
    }

    public int getSeller_negative_feedback() {
        return seller_negative_feedback;
    }

    public String getSeller_term_condition() {
        return seller_term_condition;
    }

    public Object getSeller_alert() {
        return seller_alert;
    }

    public Boolean getFor_sale() {
        return for_sale;
    }

    public List<Object> getState_description() {
        return state_description;
    }

    public String getLast_relist_at() {
        return last_relist_at;
    }

    public Specs getSpecs() {
        return specs;
    }

    public Boolean getForce_insurance() {
        return force_insurance;
    }

    public List<Object> getFree_shipping_coverage() {
        return free_shipping_coverage;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public int getWeight() {
        return weight;
    }

    public List<Integer> getImage_ids() {
        return image_ids;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getSmall_images() {
        return small_images;
    }

    public String getDesc() {
        return desc;
    }

    public String getCondition() {
        return condition;
    }

    public Boolean getNego() {
        return nego;
    }

    public int getStock() {
        return stock;
    }

    public Object getMinimum_negotiable() {
        return minimum_negotiable;
    }

    public int getView_count() {
        return view_count;
    }

    public int getSold_count() {
        return sold_count;
    }

    public int getWaiting_payment() {
        return waiting_payment;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public String getCreated_at() {
        return created_at;
    }

    public List<Object> getProduct_sin() {
        return product_sin;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getCategory_structure() {
        return category_structure;
    }

    public int getInterest_count() {
        return interest_count;
    }
}
