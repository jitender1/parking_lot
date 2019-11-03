package com.gojek.parking.exceptions;

public class ServiceException extends Exception {

    public ServiceException(String error) {
        super(error);
    }
}