package com.jet.peoplemanagement.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class BusinessException extends RuntimeException  {

    public BusinessException(String message) {
        super(BusinessException.generateMessage(message));
    }

    private static String generateMessage(String message) {
        return message;
    }

}