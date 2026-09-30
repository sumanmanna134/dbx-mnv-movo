package com.movo.transactionstore.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movo.transactionstore.infrastructure.web.constant.Field;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be greater than 0")
        @JsonProperty(Field.AMOUNT)
        BigDecimal amount,

        @NotBlank(message = "currency is required")
        @JsonProperty(Field.CURRENCY)
        String currency,

        @NotBlank(message = "type is required (DEBIT | CREDIT)")
        @JsonProperty(Field.TYPE)
        String type,

        @NotBlank(message = "category is required")
        @JsonProperty(Field.CATEGORY)
        String category,

        @JsonProperty(Field.MERCHANT)
        String merchant,

        @JsonProperty(Field.SOURCE)
        String source,

        @JsonProperty(Field.STATUS)
        String status,

        @JsonProperty(Field.FINGERPRINT)
        String fingerPrint,

        @JsonProperty(Field.CARD_ID)
        UUID cardId,

        @JsonProperty(Field.ACCOUNT_ID)
        UUID accountId,

        @JsonProperty(Field.NOTES)
        String notes,

        @JsonProperty(Field.CONFIDENCE_SCORE)
        BigDecimal confidenceScore,


        @JsonProperty(Field.TXN_TIME)
        Instant txnTime
) {
}
