package com.movo.transactionstore.domain.dto;

public enum TransactionType {
    CREDIT,
    DEBIT;

    public static TransactionType from(String value) {
        return TransactionType.valueOf(value.toUpperCase());
    }
}
