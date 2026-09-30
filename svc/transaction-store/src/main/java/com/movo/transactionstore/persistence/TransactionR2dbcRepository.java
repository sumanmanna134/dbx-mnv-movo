package com.movo.transactionstore.persistence;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface TransactionR2dbcRepository extends R2dbcRepository<TransactionEntity, UUID>, CustomTransactionRepository {

    Mono<TransactionEntity> findByUserIdAndId(UUID userId, UUID id);

    Mono<TransactionEntity> findByUserIdAndFingerprint(UUID userId, String fingerprint);

    Mono<TransactionEntity> findBySagaId(UUID sagaId);

}
