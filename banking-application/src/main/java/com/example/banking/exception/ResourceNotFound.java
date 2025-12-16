package com.example.banking.exception;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String msg) {
        super(msg);
    }
}
