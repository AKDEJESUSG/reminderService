package com.skipit.remainder.skipitreminder.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.skipit.remainder.skipitreminder.util.ExceptionFormatUtils;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class ResponseEntityHandler extends ResponseEntityExceptionHandler {

    private final ExceptionFormatUtils exUtil;

    public ResponseEntityHandler(ExceptionFormatUtils exUtil) {
        this.exUtil = exUtil;
    }

    @ExceptionHandler(ReminderNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(ReminderNotFoundException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ReminderBadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(ReminderBadRequestException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(ReminderValidationException.class)
    public ResponseEntity<ErrorResponse> noValidReminder(ReminderValidationException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getMessage();
        log.error(error);
        ErrorResponse err = ErrorResponse.builder()
                .message(exUtil.getException(error, 2))
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ObjectError> errorlist = ex.getBindingResult().getAllErrors();
        StringBuilder errors = new StringBuilder();
        errorlist.forEach(i -> errors.append(i + " :"));
        log.error(ex.getMessage());
        ErrorResponse err = ErrorResponse.builder()
                .message(errors.toString())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointer(NullPointerException ex) {
        String error = ex.getMessage();
        log.error(error);
        ErrorResponse err = ErrorResponse.builder()
                .message(exUtil.getException(error))
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(err);
    }
}
