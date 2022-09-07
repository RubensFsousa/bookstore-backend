package com.bookstore.rubens.model.Enum;

import lombok.Getter;

@Getter
public enum StatusRent {
    ON_TIME ("ON TIME"),
    DELAYED ("DELAYED"),
    IN_PROGRESS ("IN PROGRESS");

    private final String status;

    public String getRentStatus(){
        return this.status;
    }
    StatusRent(String status) {
        this.status = status;
    }
}
