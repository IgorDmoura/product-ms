package com.compasso.teste.productms.business.exception;

import com.compasso.teste.productms.dto.ErrorDTO;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    private static final String MESSAGE = "The request is not valid";

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException ex){
        log.error("M=handleProductNotFoundException, message=Error searching product, productId={}",ex.getProductId());

        String message = String.format("No product found with Id: %s",ex.getProductId());
        ErrorDTO response = new ErrorDTO(HttpStatus.NOT_FOUND.value(), message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException mav){
        log.error("M=MethodArgumentNotValidException, message=Error argument not valid");

        ErrorDTO response = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), MESSAGE);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException cve){
        log.error("M=ConstraintViolationException, message=Error constraint violation");

        ErrorDTO response = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), MESSAGE);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorDTO> handleJsonParseException(JsonParseException mav){
        log.error("M=JsonParseException, message=Error json parse");

        ErrorDTO response = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), MESSAGE);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException hmn){
        log.error("M=HttpMessageNotReadableException, message=Error in formatting the request");

        String message = "The formatting of the request is not valid";
        ErrorDTO response = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}