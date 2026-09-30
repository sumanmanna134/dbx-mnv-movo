package com.movo.transactionstore.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.mov.money.Money;

public class Transaction {
    private final TransactionId transactionId;
    private final UUID sagaId;
    private final UUID userId;
    private final Money amount;
    private final TransactionType types;
    private final TransactionCategory category;
    private final String merchant;
    private final TransactionSource source;
    private final TransactionStatus status;
    private final String fingerprint;
    private final UUID cardId;
    private final UUID accountId;
    private final String notes;
    private final boolean confirmedByUser;
    private final BigDecimal confidenceScore;
    private final Instant txnTime;
    private final Instant createdAt;
    private final Instant updatedAt;


    public Transaction(TransactionId transactionId, UUID sagaId, UUID userId, Money amount, TransactionType types, TransactionCategory category, String merchant, TransactionSource source, TransactionStatus status, String fingerprint, UUID cardId, UUID accountId, String notes, boolean confirmedByUser, BigDecimal confidenceScore, Instant txnTime, Instant createdAt, Instant updatedAt) {
        this.transactionId = Objects.requireNonNull(transactionId, "transactionId must not be null");
        this.sagaId = Objects.requireNonNull(sagaId, "sagaId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.amount = Objects.requireNonNull(amount.positive(), "amount must not be null");
        this.types = Objects.requireNonNull(types, "types must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.merchant = merchant!=null ? merchant.trim().toUpperCase() : "";
        this.source = Objects.requireNonNull(source, "source must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.fingerprint = Objects.requireNonNull(fingerprint, "fingerprint must not be null");
        this.cardId = cardId;
        this.accountId = accountId;
        this.notes = notes;
        this.confirmedByUser = confirmedByUser;
        this.confidenceScore = confidenceScore!=null?confidenceScore:BigDecimal.ONE;
        this.txnTime = txnTime;
        this.createdAt = createdAt!=null?createdAt:Instant.now();
        this.updatedAt = updatedAt!=null?updatedAt:Instant.now();
    }

    public static Transaction create(
            UUID userId,
            Money amount,
            TransactionType type,
            TransactionCategory category,
            String merchant,
            TransactionSource source,
            String fingerprint,
            UUID cardId,
            UUID accountId,
            String notes,
            BigDecimal confidenceScore,
            Instant txnTime
    ) {
        Instant now = Instant.now();
        return new Transaction(
                TransactionId.generate(),
                UUID.randomUUID(), // New Saga ID
                userId,
                amount,
                type,
                category,
                merchant,
                source,
                TransactionStatus.PENDING_SETTLEMENT,
                fingerprint,
                cardId,
                accountId,
                notes,
                false,
                confidenceScore,
                txnTime != null ? txnTime : now,
                now,
                now
        );
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getTypes() {
        return types;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public String getMerchant() {
        return merchant;
    }

    public TransactionSource getSource() {
        return source;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public UUID getCardId() {
        return cardId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isConfirmedByUser() {
        return confirmedByUser;
    }

    public BigDecimal getConfidenceScore() {
        return confidenceScore;
    }

    public Instant getTxnTime() {
        return txnTime;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
