package com.movo.constants;

/**
 * Database entity constants — table names and column names
 * used in @Table / @Column annotations and raw SQL across all services.
 */
public interface EntityConstant {

    // =========================================================
    // Table Names
    // =========================================================
    String TABLE_TRANSACTIONS = "transactions";

    // =========================================================
    // Column Names
    // =========================================================
    String COL_ID = "id";
    String COL_SAGA_ID = "saga_id";
    String COL_USER_ID = "user_id";
    String COL_AMOUNT = "amount";
    String COL_CURRENCY = "currency";
    String COL_TYPE = "type";
    String COL_CATEGORY = "category";
    String COL_MERCHANT = "merchant";
    String COL_SOURCE = "source";
    String COL_STATUS = "status";
    String COL_FINGERPRINT = "fingerprint";
    String COL_CARD_ID = "card_id";
    String COL_ACCOUNT_ID = "account_id";
    String COL_NOTES = "notes";
    String COL_CONFIRMED_BY_USER = "confirmed_by_user";
    String COL_CONFIDENCE_SCORE = "confidence_score";
    String COL_TXN_TIME = "txn_time";
    String COL_CREATED_AT = "created_at";
    String COL_UPDATED_AT = "updated_at";
}
