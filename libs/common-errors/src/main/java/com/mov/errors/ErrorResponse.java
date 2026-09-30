package com.mov.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String code, String message, String traceId, Instant timestamp, Object details) {

    public static ErrorResponse of(String code, String message, String traceId){
        return new ErrorResponse(code, message, traceId, Instant.now(), null);
    }

    public static ErrorResponse of(String code, String message, String traceId, Object details){
        return new ErrorResponse(code, message, traceId, Instant.now(), details);
    }



}
