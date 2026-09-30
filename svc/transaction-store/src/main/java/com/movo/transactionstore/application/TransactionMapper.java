package com.movo.transactionstore.application;

import com.mov.money.Currency;
import com.mov.money.Money;
import com.movo.transactionstore.domain.dto.*;
import com.movo.transactionstore.infrastructure.web.dto.TransactionResponse;
import com.movo.transactionstore.persistence.TransactionEntity;

public class TransactionMapper {
    private TransactionMapper() {}

    public static Transaction toDomain(TransactionEntity entity){
        if(entity == null) return null;

        Currency currency = Currency.valueOf(entity.getCurrency());
        Money amount = Money.of(entity.getAmount(), currency);

        return new Transaction(
                TransactionId.of(entity.getId()),
                entity.getSagaId(),
                entity.getUserId(),
                amount,
                TransactionType.valueOf(entity.getType()),
                TransactionCategory.valueOf(entity.getCategory()),
                entity.getMerchant(),
                TransactionSource.valueOf(entity.getSource()),
                TransactionStatus.valueOf(entity.getStatus()),
                entity.getFingerprint(),
                entity.getCardId(),
                entity.getAccountId(),
                entity.getNotes(),
                Boolean.TRUE.equals(entity.getConfirmedByUser()),
                entity.getConfidenceScore(),
                entity.getTxnTime(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()

        );

    }

    public static TransactionEntity toEntity(Transaction domain, boolean isNew ){
        if(domain == null) return null;
        return new TransactionEntity(
                domain.getTransactionId().value(),
                domain.getSagaId(),
                domain.getUserId(),
                domain.getAmount().getAmount(),
                domain.getAmount().getCurrency().name(),
                domain.getTypes().name(),
                domain.getCategory().name(),
                domain.getMerchant(),
                domain.getSource().name(),
                domain.getStatus().name(),
                domain.getFingerprint(),
                domain.getCardId(),
                domain.getAccountId(),
                domain.getNotes(),
                domain.isConfirmedByUser(),
                domain.getConfidenceScore(),
                domain.getTxnTime(),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                isNew

        );
    }


    public static TransactionResponse  toResponse(Transaction domain){
        if(domain == null) return null;
        return new TransactionResponse(
                domain.getTransactionId().value(),
                domain.getSagaId(),
                domain.getUserId(),
                domain.getAmount().getAmount(),
                domain.getAmount().getCurrency().name(),
                domain.getTypes().name(),
                domain.getCategory().name(),
                domain.getMerchant(),
                domain.getSource().name(),
                domain.getStatus().name(),
                domain.getFingerprint(),
                domain.getCardId(),
                domain.getAccountId(),
                domain.getNotes(),
                domain.isConfirmedByUser(),
                domain.getConfidenceScore(),
                domain.getTxnTime(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
