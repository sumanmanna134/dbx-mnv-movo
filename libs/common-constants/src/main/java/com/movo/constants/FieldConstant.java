package com.movo.constants;

/**
 * Field-name constants used in JSON serialization / deserialization
 * and web-layer query parameters across all microservices.
 */
public interface FieldConstant {
    String ID = "id";
    String MERCHANT = "merchant";
    String SAGA_ID = "sagaId";
    String USER_ID = "userId";
    String AMOUNT = "amount";
    String CURRENCY = "currency";
    String TYPE = "type";
    String SOURCE = "source";
    String CATEGORY = "category";
    String STATUS = "status";
    String FINGERPRINT = "fingerprint";
    String CARD_ID = "cardId";
    String NOTES = "notes";
    String CONFIRMED_BY_USER = "confirmedByUser";
    String CONFIDENCE_SCORE = "confidenceScore";
    String TXN_TIME = "txnTime";
    String CREATED_AT = "createdAt";
    String UPDATED_AT = "updatedAt";
    String ACCOUNT_ID = "accountId";
}
