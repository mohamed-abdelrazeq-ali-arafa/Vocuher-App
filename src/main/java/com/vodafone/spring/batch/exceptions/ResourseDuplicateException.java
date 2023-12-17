package com.vodafone.spring.batch.exceptions;

import org.springframework.http.HttpStatus;

public class ResourseDuplicateException  extends RuntimeException{
    public ResourseDuplicateException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus(){
        return HttpStatus.FOUND;
    }

}
