package com.movo.transactionstore.domain.dto;

import java.util.Objects;
import java.util.UUID;

public record TransactionId(UUID value) {
    public TransactionId {
        Objects.requireNonNull(value, "TransactionId value must not be null");
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }

    public static TransactionId of(UUID uuid) {
        return new TransactionId(uuid);
    }

    public static TransactionId of(String uuid) {
        return new TransactionId(UUID.fromString(uuid));
    }

    @Override
    public final String toString() {
        return value.toString();
    }

}
