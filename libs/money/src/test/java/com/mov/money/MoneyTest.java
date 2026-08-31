package com.mov.money;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@DisplayName("com.mov.money.Money Value Object")
public class MoneyTest {


    @Nested
    @DisplayName("Construction")
    class Construction {
        @Test
        @DisplayName("should normalize scale to 2 decimal places")
        public void shouldNormalizeScaleTo2DecimalPlaces() {
           Money m = Money.of("100", Currency.INR);

           assertThat(m.getAmount().scale()).isEqualTo(2);
           assertThat(m.getAmount().toPlainString()).isEqualTo("100.00");

        }


        @Test
        @DisplayName("should apply HALF_EVEN rounding on construction")
        void shouldApplyHalfEvenRounding() {
            // 100.005 rounds to 100.00 (HALF_EVEN — 0 is even)
            Money m1 = Money.of(new BigDecimal("100.005"), Currency.INR);
            assertThat(m1.getAmount().toPlainString()).isEqualTo("100.00");

            // 100.015 rounds to 100.02 (HALF_EVEN — 2 is even)
            Money m2 = Money.of(new BigDecimal("100.015"), Currency.INR);
            assertThat(m2.getAmount().toPlainString()).isEqualTo("100.02");
        }

        @Test
        @DisplayName("should reject null amount")
        void shouldRejectNullAmount() {
            assertThatNullPointerException().isThrownBy(()->Money.of((BigDecimal) null, Currency.INR))
                    .withMessageContaining("amount");
        }

        @Test
        @DisplayName("should reject null currency")
        void shouldRejectNullCurrency(){
            assertThatNullPointerException().isThrownBy(()-> Money.of("100",null))
                    .withMessageContaining("currency");
        }

        @Test
        @DisplayName("should create zero money")
        void shouldCreateZeroMoney(){
            Money m = Money.zero(Currency.INR);
            assertThat(m.isZero()).isTrue();
            assertThat(m.getAmount().toPlainString()).isEqualTo("0.00");
        }

        @Nested
        @DisplayName("Arithmetic")
        class Arithmetic {
            @Test
            @DisplayName("add should return sum of two Money values")
            void shouldAddTwoMoneyValues(){
                Money m1 = Money.of("100000", Currency.INR);
                Money m2 = Money.of("200000", Currency.INR);

                assertThat(m1.add(m2)).isEqualTo(Money.of("300000", Currency.INR));

            }

            @Test
            @DisplayName("subtract should return difference")
            void subtractShouldReturnDifference(){
                Money m1 = Money.of("100000.00", Currency.INR);
                Money m2 = Money.of("45000.00", Currency.INR);

                assertThat(m1.subtract(m2)).isEqualTo(Money.of("55000.00", Currency.INR));

            }
            @Test
            @DisplayName("subtract can produce negative Money")
            void subtractCanProduceNegative() {
                Money a = Money.of("1000.00", Currency.INR);
                Money b = Money.of("2000.00", Currency.INR);
                Money result = a.subtract(b);
                assertThat(result.isNegative()).isTrue();
                assertThat(result).isEqualTo(Money.of("-1000.00", Currency.INR));
            }
        }




    }
}
