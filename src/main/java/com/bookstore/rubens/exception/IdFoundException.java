package com.bookstore.rubens.exception;

import javax.persistence.EntityNotFoundException;

public class IdFoundException extends EntityNotFoundException {

    public IdFoundException(Long id){
        super(String.format("Id not found: ", id));
    }
}
