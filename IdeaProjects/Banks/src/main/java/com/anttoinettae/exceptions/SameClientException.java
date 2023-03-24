package com.anttoinettae.exceptions;

public class SameClientException extends Exception {
    public SameClientException(String message){
        super(message);
    }
}
