package com.movo.transactionstore.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movo.transactionstore.infrastructure.web.constant.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
        @JsonProperty(Field.ID) UUID id,
        @JsonProperty(Field.SAGA_ID) UUID sagaId,
        @JsonProperty(Field.USER_ID) UUID userId,
        @JsonProperty(Field.AMOUNT) BigDecimal amount,
        @JsonProperty(Field.CURRENCY) String currency,
        @JsonProperty(Field.TYPE) String type,
        @JsonProperty(Field.CATEGORY) String category,
        @JsonProperty(Field.MERCHANT) String merchant,
        @JsonProperty(Field.SOURCE) String source,
        @JsonProperty(Field.STATUS) String status,
        @JsonProperty(Field.FINGERPRINT) String fingerprint,
        @JsonProperty(Field.CARD_ID) UUID cardId,
        @JsonProperty(Field.ACCOUNT_ID) UUID accountId,
        @JsonProperty(Field.NOTES) String notes,
        @JsonProperty(Field.CONFIRMEDBYUSER) boolean confirmedByUser,
        @JsonProperty(Field.CONFIDENCE_SCORE) BigDecimal confidenceScore,
        @JsonProperty(Field.TXN_TIME)Instant txnTime,
        @JsonProperty(Field.CREATED_AT) Instant createdAt,
        @JsonProperty(Field.UPDATED_AT) Instant updatedAt




        ) {

        public static TransactionResponse dummy(){
            return new TransactionResponse(
                        UUID.randomUUID(),                         // id
                        UUID.randomUUID(),                         // sagaId
                        UUID.randomUUID(),                         // userId
                        new BigDecimal("2499.00"),                // amount
                        "INR",                                     // currency
                        "DEBIT",                                   // type
                        "SHOPPING",                                // category
                        "Amazon",                                  // merchant
                        "SMS",                                     // source
                        "PENDING_CONFIRMATION",                   // status
                        "8f7f3f7f2e5d4c3b9a1f0e8d7c6b5a4f",       // fingerprint
                        UUID.randomUUID(),                         // cardId
                        UUID.randomUUID(),                         // accountId
                        "Amazon purchase detected from SMS",      // notes
                        false,                                     // confirmedByUser
                        new BigDecimal("0.98"),                   // confidenceScore
                        Instant.now().minusSeconds(3600),         // txnTime
                        Instant.now().minusSeconds(3500),         // createdAt
                        Instant.now()                              // updatedAt
                );
        }
}
