package com.movo.transactionstore.infrastructure.web.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface Field {
    public static final String ID = "id";
    public static final String MERCHANT = "merchant";
    public static final String SAGA_ID = "sagaId";
    public static final String USER_ID = "userId";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String TYPE = "type";
    public static final String SOURCE = "source";
    public static final String CATEGORY = "category";
    public static final String STATUS = "status";
    public static final String FINGERPRINT = "fingerprint";
    public static final String CARD_ID = "cardId";
    public static final String NOTES = "notes";
    public static final String CONFIRMEDBYUSER = "confirmedByUser";
    public static final String CONFIDENCE_SCORE = "confidenceScore";
    public static final String TXN_TIME =  "txnTime";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String ACCOUNT_ID = "accountId";

}
