package org.example.izzy.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.izzy.exception.InvalidCredentialsException;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.dto.response.general.ErrorResponse;
import org.example.izzy.model.dto.response.general.FieldErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
400 Bad Request: Invalid input or validation errors.
401 Unauthorized: Authentication failure.
403 Forbidden: Authorization failure.
404 Not Found: Resource not found.
500 Internal Server Error: Unexpected server errors.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                exception.getClass().getSimpleName()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    //          🔵🔵🔵🔵🔵🔵              FIELD ERROR           🔵🔵🔵🔵🔵🔵
    // Handles @Valid and @Validated DTO errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        FieldErrorResponse response = new FieldErrorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                LocalDateTime.now(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handles direct ConstraintViolationException (e.g. from @RequestParam, @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString(); // might include methodName.paramName
            String message = violation.getMessage();
            errors.put(field, message);
        });

        FieldErrorResponse response = new FieldErrorResponse(
                "Constraint violation",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                LocalDateTime.now(),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


//  ‼️‼️‼️‼️‼️    AUTHENTICATION RELATED ONES ‼️‼️‼️‼️‼️

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledUser(DisabledException ex) {
        ErrorResponse err = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                HttpStatus.FORBIDDEN.name()
        );
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.name());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    //🔴🔴🔴🔴🔴🔴    GENERIC EXCEPTION HANDLER 🔴🔴🔴🔴
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        logger.warn("Unexpected error occurred [ 500-Internal Server Error ]", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "🔴An unexpected error occurred!🔴",
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    /**
     * Handles database constraint violations (e.g., unique constraints).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<FieldErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        if (ex.getMessage().contains("phone_number")) {
            fieldErrors.put("phoneNumber", "Phone number already registered");
        }
        if (ex.getMessage().contains("attachment_id")) {
            fieldErrors.put("attachment_id", "Duplicate key value violates unique constraint");
        } else {
            fieldErrors.put("general", "Failed to save data due to database constraint");
        }

        FieldErrorResponse response = new FieldErrorResponse(
                "Database constraint violation",
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                request.getRequestURI(),
                LocalDateTime.now(),
                fieldErrors
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
