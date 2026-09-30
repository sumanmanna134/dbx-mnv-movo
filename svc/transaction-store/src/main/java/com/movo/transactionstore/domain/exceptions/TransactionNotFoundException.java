package com.movo.transactionstore.domain.exceptions;


import com.mov.errors.ErrorCode;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException{
    private final UUID transactionId;

    public TransactionNotFoundException(UUID transactionId){
        super("Transaction with id " + transactionId + " not found");
        this.transactionId = transactionId;

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getErrorCode(){
        return ErrorCode.TRANSACTION_NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
