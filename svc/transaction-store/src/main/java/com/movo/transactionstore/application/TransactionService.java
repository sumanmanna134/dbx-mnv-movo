package com.movo.transactionstore.application;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.Locale;
import java.util.UUID;

import com.mov.events.TransactionCreatedEvent;
import com.mov.money.Money;
import com.movo.transactionstore.domain.dto.Transaction;
import com.movo.transactionstore.domain.dto.TransactionCategory;
import com.movo.transactionstore.domain.dto.TransactionSource;
import com.movo.transactionstore.domain.dto.TransactionType;
import com.movo.transactionstore.domain.exceptions.DuplicateTransactionException;

import com.movo.transactionstore.persistence.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mov.money.Currency;
import com.movo.transactionstore.infrastructure.web.dto.CreateTransactionRequest;
import com.movo.transactionstore.infrastructure.web.dto.TransactionResponse;
import com.movo.transactionstore.persistence.TransactionR2dbcRepository;

import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import javax.xml.crypto.AlgorithmMethod;

@Service
public class TransactionService {
    // logger
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final ObjectMapper mapper;
    private final TransactionalOperator transactionalOperator;

    private final TransactionR2dbcRepository repository;

    public TransactionService(ObjectMapper objectMapper, TransactionR2dbcRepository repository, TransactionalOperator transactionalOperator) {
        this.mapper = objectMapper;
        this.repository = repository;
        this.transactionalOperator = transactionalOperator;
    }

    public Mono<TransactionResponse> createTransaction(UUID userId, CreateTransactionRequest request) {
        Currency currency = Currency.valueOf(request.currency().toUpperCase().trim());
        Money amount = Money.of(request.amount(), currency);

        TransactionType type = TransactionType.from(request.type());
        TransactionCategory category = TransactionCategory.from(request.category());
        Instant txnTime = request.txnTime() != null ? request.txnTime() : Instant.now();
        TransactionSource source = request.source() != null
                ? TransactionSource.valueOf(request.source().toUpperCase().trim())
                : TransactionSource.MANUAL;
        String fingerprint = generateFingerPrint(userId, amount, request.merchant(), category, txnTime);
        return repository.findByUserIdAndFingerprint(userId, fingerprint)
        .flatMap(existing->Mono.<TransactionResponse> error(new DuplicateTransactionException(fingerprint)))
                .switchIfEmpty(Mono.defer(()->{
                    Transaction domainTxn = Transaction.create(
                            userId,
                            amount,
                            type,
                            category,
                            request.merchant(),
                            source,
                            fingerprint,
                            request.cardId(),
                            request.accountId(),
                            request.notes(),
                            request.confidenceScore(),
                            txnTime
                    );

                    TransactionEntity entity = TransactionMapper.toEntity(domainTxn, true);

                    TransactionCreatedEvent event = new TransactionCreatedEvent(                    UUID.randomUUID(),
                            domainTxn.getTransactionId().value(),
                            domainTxn.getSagaId(),
                            userId,
                            Instant.now(),
                            amount,
                            type.name(),
                            category.name(),
                            domainTxn.getMerchant(),
                            source.name(),
                            request.cardId(),
                            request.accountId(),
                            domainTxn.isConfirmedByUser(),
                            domainTxn.getConfidenceScore());

                    return repository.save(entity)
                            .map(TransactionMapper::toDomain)
                            .map(TransactionMapper::toResponse)
                            .as(transactionalOperator::transactional);
                }));

                

    }

    private String generateFingerPrint(UUID userId, Money amount, String merchant, TransactionCategory category,
            Instant txnTime) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String raw = String.format("%s:%s:%s:%s:%s:%d", userId, amount.getAmount().toPlainString(),
                    amount.getCurrency().getSymbol(),
                    merchant != null ? merchant.trim().toLowerCase() : "", category.name(),
                    txnTime.truncatedTo(ChronoUnit.HOURS).toEpochMilli());

            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }

    }

}
