package com.gojek.parking.exceptions;

public class DaoException extends Exception {

    public DaoException(String error){
        super(error);
    }
}