package com.khamzin.orderservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<OrderErrorResponse> handleException(InventoryNotFoundException e) {
        OrderErrorResponse errorResponse = OrderErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .name(e.getMessage())
                .date(new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
