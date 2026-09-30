package com.mov.money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    /**
     * Standard display scale for com.mov.money.Money (2 decimal places)
     */
    public static final int DISPLAY_SCALE=2;

    public static final RoundingMode FINANCIAL_ROUNDING = RoundingMode.HALF_EVEN;

    public static final MathContext COMPUTATION_CONTEXT = MathContext.DECIMAL128;

    private final BigDecimal amount;
    private final Currency currency;


    @JsonCreator
    private Money(@JsonProperty("money") BigDecimal amount,
                  @JsonProperty("currency") Currency currency){
        this.amount = Objects.requireNonNull(amount, "amount must not be null").setScale(DISPLAY_SCALE, FINANCIAL_ROUNDING);
        this.currency = Objects.requireNonNull(currency, "currency must not be null");


    }

    public static Money of(BigDecimal amount, Currency currency){
        return new Money(amount,currency);
    }

    public static Money ofMinorUnits(long minorUnits, Currency currency){
        BigDecimal amount = BigDecimal.valueOf(minorUnits)
                .movePointLeft(currency.getMinorUnitScale());
        return new Money(amount,currency);
    }

    public static Money zero(Currency currency){
        return new Money(BigDecimal.ZERO,currency);
    }

    public static Money of(String number,  Currency currency) {
        return new Money(new BigDecimal(number),currency);
    }


    //operations
    public Money add(Money other){
        assertSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public Money subtract(Money other){
        assertSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), currency);
    }

    public Money multiply(BigDecimal factor){
        return new Money(this.amount.multiply(factor, COMPUTATION_CONTEXT)
                .setScale(DISPLAY_SCALE, FINANCIAL_ROUNDING),
                currency);
    }

    public Money divide(int divisor){
        return new Money(this.amount.divide(BigDecimal.valueOf(divisor), DISPLAY_SCALE, FINANCIAL_ROUNDING), currency);
    }

    public Money negate(){
        return new Money(this.amount.negate(), currency);
    }

    public Money abs(){
        return new Money(this.amount.abs(), currency);
    }

    public boolean isGreaterThan(Money other){
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThan(Money other){
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isZero(){
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
    public boolean isNegative(){
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isPositive(){
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isStrictlyPositive(){
        return this.amount.compareTo(BigDecimal.ZERO) >=0;
    }

    public boolean isGreaterThanOrEqualTo(Money other){
        return this.amount.compareTo(other.amount) >= 0;
    }

    public long toMinorUnits(){
        return amount.movePointLeft(currency.getMinorUnitScale())
                .longValueExact();
    }

    public BigDecimal toHighPrecisionAmount(){
        return this.amount.round(COMPUTATION_CONTEXT);
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("currency")
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money other)) return false;
        return this.amount.compareTo(other.amount) == 0
                && this.currency == other.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString() {
        return currency.getSymbol()+amount.toPlainString();

    }

    public Money positive(){
        if(!this.isStrictlyPositive()){
            throw new IllegalArgumentException("Transaction amount must be strictly positive, was: " + amount);
        }else {
            return this;
        }
    }

    private void assertSameCurrency(Money other) {
        if (this.currency != other.currency) {
            throw new CurrencyMismatchException(this.currency, other.currency);
        }
    }
}
