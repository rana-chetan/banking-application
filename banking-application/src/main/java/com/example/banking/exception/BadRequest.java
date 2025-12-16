package com.example.banking.exception;

public class BadRequest extends RuntimeException {

    public BadRequest(String msg) {
        super(msg);
    }
}