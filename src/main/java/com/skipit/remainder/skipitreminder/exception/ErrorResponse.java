package com.skipit.remainder.skipitreminder.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private Instant timestamp;
    private HttpStatus statusCode;
}
