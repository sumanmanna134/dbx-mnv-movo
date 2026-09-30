package com.mov.errors;

public class ErrorCode {
    public ErrorCode() {}

    public static final String VALIDATION_ERROR="VALIDATION_ERROR";
    public static final String NOT_FOUND="NOT_FOUND";
    public static final String INTERNAL_SERVER_ERROR="INTERNAL_SERVER_ERROR";
    public static final String CONFLICT="CONFLICT";
    public static final String UNAUTHORIZED="UNAUTHORIZED";
    public static final String FORBIDDEN="FORBIDDEN";
    public static final String RATE_LIMITED="RATE_LIMITED";

    // =========================================================
    // Transaction
    // =========================================================
    public static final String TRANSACTION_NOT_FOUND = "TRANSACTION_NOT_FOUND";
    public static final String TRANSACTION_DUPLICATE = "TRANSACTION_DUPLICATE";
    public static final String TRANSACTION_ALREADY_CONFIRMED = "TRANSACTION_ALREADY_CONFIRMED";
    public static final String TRANSACTION_INVALID_AMOUNT = "TRANSACTION_INVALID_AMOUNT";

}
