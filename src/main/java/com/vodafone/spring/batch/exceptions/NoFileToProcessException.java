package com.vodafone.spring.batch.exceptions;

public class NoFileToProcessException  extends RuntimeException{
    public NoFileToProcessException(String message) {
        super(message);
    }
}
