package com.movo.transactionstore.persistence;

import java.time.Instant;
import java.util.UUID;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.movo.transactionstore.domain.constant.TransferField;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {
    private final DatabaseClient databaseClient;

    public CustomTransactionRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<TransactionEntity> findTransactions(UUID userId, Instant fromDate, Instant toDate, String category, String type, String status, int limit, int offset) {
        StringBuilder sql = new StringBuilder(
                "SELECT id, saga_id, user_id, amount, currency, type, category, merchant, " +
                        "source, status, fingerprint, card_id, account_id, notes, confirmed_by_user, " +
                        "confidence_score, txn_time, created_at, updated_at " +
                        "FROM transactions WHERE user_id = :userId"
        );

        appendFilter(sql, fromDate, toDate, category, type, status);
        sql.append(" ORDER BY txn_time DESC LIMIT :limit OFFSET :offset");
        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(sql.toString())
                .bind("userId", userId)
                .bind("limit", limit)
                .bind("offset", offset);

        spec = bindFilter(spec, fromDate, toDate, category, type, status);
        return spec.map((row, metadata) -> new TransactionEntity(
                row.get(TransferField.ID.getName(), TransferField.ID.getType()),
                row.get(TransferField.SAGA_ID.getName(), TransferField.SAGA_ID.getType()),
                row.get(TransferField.USER_ID.getName(), TransferField.USER_ID.getType()),
                row.get(TransferField.AMOUNT.getName(), TransferField.AMOUNT.getType()),
                row.get(TransferField.CURRENCY.getName(), TransferField.CURRENCY.getType()),
                row.get(TransferField.TYPE.getName(), TransferField.TYPE.getType()),
                row.get(TransferField.CATEGORY.getName(), TransferField.CATEGORY.getType()),
                row.get(TransferField.MERCHANT.getName(), TransferField.MERCHANT.getType()),
                row.get(TransferField.SOURCE.getName(), TransferField.SOURCE.getType()),
                row.get(TransferField.STATUS.getName(), TransferField.STATUS.getType()),
                row.get(TransferField.FINGERPRINT.getName(), TransferField.FINGERPRINT.getType()),
                row.get(TransferField.CARD_ID.getName(), TransferField.CARD_ID.getType()),
                row.get(TransferField.ACCOUNT_ID.getName(), TransferField.ACCOUNT_ID.getType()),
                row.get(TransferField.NOTES.getName(), TransferField.NOTES.getType()),
                row.get(TransferField.CONFIRMED_BY_USER.getName(), TransferField.CONFIRMED_BY_USER.getType()),
                row.get(TransferField.CONFIDENCE_SCORE.getName(), TransferField.CONFIDENCE_SCORE.getType()),
                row.get(TransferField.TXN_TIME.getName(), TransferField.TXN_TIME.getType()),
                row.get(TransferField.CREATED_AT.getName(), TransferField.CREATED_AT.getType()),
                row.get(TransferField.UPDATED_AT.getName(), TransferField.UPDATED_AT.getType()),
                false
        )).all();
    }

    @Override
    public Mono<Integer> countTransactionCounts(UUID userId, Instant fromDate, Instant toDate, String category, String type, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM transactions WHERE user_id = :userId");
        appendFilter(sql, fromDate, toDate, category, type, status);

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(sql.toString())
                .bind("userId", userId);
        spec = bindFilter(spec, fromDate, toDate, category, type, status);

        return spec.map((row, metadata) -> {
            Number count = row.get(0, Number.class);
            return count != null ? count.intValue() : 0;
        }).one();
    }

    private void appendFilter(StringBuilder sql, Instant fromDate, Instant toDate, String category, String type, String status) {
        if (fromDate != null) sql.append(" AND txn_time >= :fromDate");
        if (toDate != null) sql.append(" AND txn_time <= :toDate");
        if (category != null) sql.append(" AND category = :category");
        if (type != null) sql.append(" AND type = :type");
        if (status != null) sql.append(" AND status = :status");
    }

    private DatabaseClient.GenericExecuteSpec bindFilter(DatabaseClient.GenericExecuteSpec spec, Instant fromDate, Instant toDate, String category, String type, String status) {
        if (fromDate != null) spec = spec.bind("fromDate", fromDate);
        if (toDate != null) spec = spec.bind("toDate", toDate);
        if (category != null) spec = spec.bind("category", category);
        if (type != null) spec = spec.bind("type", type);
        if (status != null) spec = spec.bind("status", status);
        return spec;
    }
}
