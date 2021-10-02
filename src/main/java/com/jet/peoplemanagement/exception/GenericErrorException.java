package com.jet.peoplemanagement.exception;

public class GenericErrorException extends RuntimeException  {

    public GenericErrorException(String message) {
        super(GenericErrorException.generateMessage(message));
    }

    private static String generateMessage(String message) {
        return message;
    }

}