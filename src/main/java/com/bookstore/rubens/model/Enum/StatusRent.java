package com.bookstore.rubens.model.Enum;

import lombok.Getter;

@Getter
public enum StatusRent {
    ENTREGUE ("ON TIME"),
    ATRASADO ("DELAYED"),
    LENDO ("IN PROGRESS");

    private final String status;

    public String getRentStatus(){
        return this.status;
    }

    StatusRent(String status) {
        this.status = status;
    }
}
