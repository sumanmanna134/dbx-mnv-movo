package com.movo.transactionstore.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transactions")
public class TransactionEntity implements Persistable<UUID> {

    @Id
    @Column("id")
    private UUID id;

    @Column("saga_id")
    private UUID sagaId;

    @Column("user_id")
    private UUID userId;

    @Column("amount")
    private BigDecimal amount;

    @Column("currency")
    private String currency;

    @Column("type")
    private String type;

    @Column("category")
    private String category;

    @Column("merchant")
    private String merchant;

    @Column("source")
    private String source;

    @Column("status")
    private String status;

    @Column("fingerprint")
    private String fingerprint;

    @Column("card_id")
    private UUID cardId;

    @Column("account_id")
    private UUID accountId;

    @Column("notes")
    private String notes;

    @Column("confirmed_by_user")
    private Boolean confirmedByUser;

    @Column("confidence_score")
    private BigDecimal confidenceScore;

    @Column("txn_time")
    private Instant txnTime;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Transient
    private boolean isNewEntity = false;

    public TransactionEntity() {

    }

    public TransactionEntity(
            UUID id,
            UUID sagaId,
            UUID userId,
            BigDecimal amount,
            String currency,
            String type,
            String category,
            String merchant,
            String source,
            String status,
            String fingerprint,
            UUID cardId,
            UUID accountId,
            String notes,
            Boolean confirmedByUser,
            BigDecimal confidenceScore,
            Instant txnTime,
            Instant createdAt,
            Instant updatedAt,
            boolean isNewEntity) {
        this.id = id;
        this.sagaId = sagaId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.category = category;
        this.merchant = merchant;
        this.source = source;
        this.status = status;
        this.fingerprint = fingerprint;
        this.cardId = cardId;
        this.accountId = accountId;
        this.notes = notes;
        this.confirmedByUser = confirmedByUser;
        this.confidenceScore = confidenceScore;
        this.txnTime = txnTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isNewEntity = isNewEntity;
    }

    // getters
    public UUID getId() {
        return id;
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
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

    public Boolean getConfirmedByUser() {
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

    public boolean isNewEntity() {
        return isNewEntity;
    }

    // setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setSagaId(UUID sagaId) {
        this.sagaId = sagaId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setConfirmedByUser(Boolean confirmedByUser) {
        this.confirmedByUser = confirmedByUser;
    }

    public void setConfidenceScore(BigDecimal confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public void setTxnTime(Instant txnTime) {
        this.txnTime = txnTime;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setNewEntity(boolean isNewEntity) {
        this.isNewEntity = isNewEntity;
    }

    @Override
    public boolean isNew() {
        return isNewEntity;
    }

}
