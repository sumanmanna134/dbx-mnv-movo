package com.mov.money;

public class CurrencyMismatchException extends RuntimeException {
    private final Currency sourceCurrency;
    private final Currency targetCurrency;

    public CurrencyMismatchException(Currency sourceCurrency, Currency targetCurrency) {
        super(String.format(
                "Cannot operate on com.mov.money.Money with different currencies: %s vs %s. " +
                        "Explicit currency conversion must be performed before arithmetic.",
                sourceCurrency, targetCurrency
        ));
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }
    public Currency getTargetCurrency() {
        return targetCurrency;
    }
}
