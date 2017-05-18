package com.bukakado.bukakado.model.request;

import java.util.Date;

/**
 * Created by edy.h on 5/17/2017.
 */

public class NewRelationRequest {
    private String fromUserId;
    private String toUserId;

    public NewRelationRequest() {
    }

    public NewRelationRequest(String fromUserId, String toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
