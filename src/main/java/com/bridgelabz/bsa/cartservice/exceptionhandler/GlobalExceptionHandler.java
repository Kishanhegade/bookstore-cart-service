package com.bridgelabz.bsa.cartservice.exceptionhandler;

import com.bridgelabz.bsa.cartservice.exception.CartNotFoundByIdException;
import com.bridgelabz.bsa.cartservice.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(CartNotFoundByIdException.class)
    public ResponseEntity<String> handleCartNotFoundById(CartNotFoundByIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequest(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
