package com.bridgelabz.bsa.cartservice.exception;

public class CartNotFoundByIdException extends RuntimeException {

    private final String message;

    public CartNotFoundByIdException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
