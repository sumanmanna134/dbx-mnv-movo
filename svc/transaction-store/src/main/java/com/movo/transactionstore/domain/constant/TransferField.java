package com.movo.transactionstore.domain.constant;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public enum TransferField {
    ID("id", UUID.class),
    SAGA_ID("saga_id", UUID.class),
    USER_ID("user_id", UUID.class),
    AMOUNT("amount", BigDecimal.class),
    CURRENCY("currency", String.class),
    TYPE("type", String.class),
    CATEGORY("category", String.class),
    MERCHANT("merchant", String.class),
    SOURCE("source", String.class),
    STATUS("status", String.class),
    FINGERPRINT("fingerprint", String.class),
    CARD_ID("card_id", UUID.class),
    ACCOUNT_ID("account_id", UUID.class),
    NOTES("notes", String.class),
    CONFIRMED_BY_USER("confirmed_by_user", Boolean.class),
    CONFIDENCE_SCORE("confidence_score", BigDecimal.class),
    TXN_TIME("txn_time", Instant.class),
    CREATED_AT("created_at", Instant.class),
    UPDATED_AT("updated_at", Instant.class);

    private final String columnName;
    private final Class<?> columnType;


    TransferField(String columnName, Class<?> columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getName() {
        return columnName;
    }

    @SuppressWarnings("unchecked")
    public<T> Class<T> getType() {
        return (Class<T>)columnType;
    }
}
