package com.movo.transactionstore.persistence;

import java.time.Instant;
import java.util.UUID;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomTransactionRepository {
    Flux<TransactionEntity> findTransactions(
            UUID userId,
            Instant fromDate,
            Instant toDate,
            String category,
            String type,
            String status,
            int limit,
            int offset);

    Mono<Integer> countTransactionCounts(
            UUID userId,
            Instant fromDate,
            Instant toDate,
            String category,
            String type,
            String status);

}
