package com.jet.peoplemanagement.meli;

/**
 *
 */
public class ClientNotAuthenticateException extends Exception {


    public ClientNotAuthenticateException() {
        super();
    }

    public ClientNotAuthenticateException(String message) {
        super(message);
    }

    public ClientNotAuthenticateException(String message, Throwable cause) {
        super(message, cause);
    }
}
