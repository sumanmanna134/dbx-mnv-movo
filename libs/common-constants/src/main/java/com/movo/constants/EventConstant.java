package com.movo.constants;

/**
 * Event-payload field-name constants used in @JsonProperty
 * annotations across domain-event classes.
 */
public interface EventConstant {
    String EVENT_ID = "eventId";
    String AGGREGATE_ID = "aggregateId";
    String SAGA_ID = "sagaId";
    String USER_ID = "userId";
    String OCCURRED_AT = "occurredAt";
    String AMOUNT = "amount";
    String TRANSACTION_TYPE = "transactionType";
    String CATEGORY = "category";
    String MERCHANT = "merchant";
    String SOURCE = "source";
    String CARD_ID = "cardId";
    String ACCOUNT_ID = "accountId";
    String CONFIRMED_BY_USER = "confirmedByUser";
    String CONFIDENCE_SCORE = "confidenceScore";

    String TRANSACTION_CREATED = "TransactionCreated";
    String TRANSACTION_UPDATED = "TransactionUpdated";
    String TRANSACTION_DELETED = "TransactionDeleted";
    String TRANSACTION = "Transaction";
    String EVENT_TYPE = "eventType";
    String AGGREGATE_TYPE = "aggregateType";
    String SCHEMA_VERSION = "schemaVersion";
}
