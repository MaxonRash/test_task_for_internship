package com.game.exception_handling;

public class IdIsNotValidException extends RuntimeException{
    public IdIsNotValidException(String message) {
        super(message);
    }
}
