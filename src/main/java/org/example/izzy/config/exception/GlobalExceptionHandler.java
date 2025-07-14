package org.example.izzy.config.exception;

import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
400 Bad Request: Invalid input or validation errors.
401 Unauthorized: Authentication failure.
403 Forbidden: Authorization failure.
404 Not Found: Resource not found.
500 Internal Server Error: Unexpected server errors.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.warn("Unexpected error occurred [ 500-Internal Server Error ]", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "🔴An unexpected error occurred!🔴",
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                exception.getClass().getSimpleName()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
