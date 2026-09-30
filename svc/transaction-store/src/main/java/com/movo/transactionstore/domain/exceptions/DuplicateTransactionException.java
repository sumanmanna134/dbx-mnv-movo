package com.movo.transactionstore.domain.exceptions;

public class DuplicateTransactionException extends RuntimeException {
    private final String fingerPrint;

    public DuplicateTransactionException(String fingerPrint) {
        super("Duplicate transaction with fingerprint: " + fingerPrint);
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    @Override
    public String getMessage() {
        return "Duplicate transaction with fingerprint: " + fingerPrint;
    }

}
