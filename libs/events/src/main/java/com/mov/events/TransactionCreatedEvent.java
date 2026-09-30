package com.mov.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mov.money.Money;
import com.movo.constants.EventConstant;
import jdk.jfr.Event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionCreatedEvent (
    @JsonProperty(EventConstant.EVENT_ID)
    UUID eventId,

    @JsonProperty(EventConstant.AGGREGATE_ID)
    UUID aggregateId,

    @JsonProperty(EventConstant.SAGA_ID)
    UUID sagaId,

    @JsonProperty(EventConstant.USER_ID)
    UUID userId,

    @JsonProperty(EventConstant.OCCURRED_AT)
    Instant occurredAt,

    // Transaction-specific payload
    @JsonProperty(EventConstant.AMOUNT)
    Money amount,

    @JsonProperty(EventConstant.TRANSACTION_TYPE)
    String transactionType,

    @JsonProperty(EventConstant.CATEGORY) String category,
    @JsonProperty(EventConstant.MERCHANT) String merchant,
    @JsonProperty(EventConstant.SOURCE) String source,             // SMS | EMAIL | MANUAL | SPLITWISE
    @JsonProperty(EventConstant.CARD_ID) UUID cardId,               // nullable
    @JsonProperty(EventConstant.ACCOUNT_ID) UUID accountId,         // nullable
    @JsonProperty(EventConstant.CONFIRMED_BY_USER) boolean confirmedByUser,
    @JsonProperty(EventConstant.CONFIDENCE_SCORE) BigDecimal confidenceScore





) implements DomainEvent{
    @Override
    public String eventType() {
        return EventConstant.TRANSACTION_CREATED;
    }

    @Override
    public String aggregateType() {
        return EventConstant.TRANSACTION;
    }

    @Override
    public int schemaVersion() {
        return 1;
    }
}
