package com.movo.transactionstore.infrastructure.web.handler;
import com.mov.errors.ErrorCode;
import com.mov.errors.ErrorResponse;
import com.movo.transactionstore.domain.exceptions.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(TransactionNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTransactionNotFoundException(TransactionNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getErrorCode(), ex.getMessage(), exchange,null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex, ServerWebExchange exchange) {
        log.warn("Validation error: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, ex.getMessage(), exchange, null);
    }

    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDataAccessException(org.springframework.dao.DataAccessException ex, ServerWebExchange exchange) {
        log.error("Database error while processing request: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR,
                "A database error occurred. Please try again or contact support.", exchange, null);
    }

    private Mono<ResponseEntity<ErrorResponse>> buildErrorResponse(HttpStatus status, String errorCode,
                                                                   String message,
                                                                   ServerWebExchange exchange,
                                                                   Object details){
        String correlationId = exchange.getRequest().getHeaders().getFirst("Correlation-Id");
        if(correlationId==null || correlationId.isEmpty()){
            correlationId = UUID.randomUUID().toString();

        }

        ErrorResponse errorResponse = new ErrorResponse(errorCode, message,correlationId, Instant.now(),details);
        return Mono.just(ResponseEntity.status(status).body(errorResponse));

    }
}
