package com.anttoinettae.exceptions;

public class AbsentTransactionException extends Exception{
    public AbsentTransactionException(String message){
        super(message);
    }
}
