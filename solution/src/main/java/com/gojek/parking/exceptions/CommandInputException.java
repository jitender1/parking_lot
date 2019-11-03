package com.gojek.parking.exceptions;

public class CommandInputException extends Exception {
    public CommandInputException(String error) {
        super(error);
    }
}