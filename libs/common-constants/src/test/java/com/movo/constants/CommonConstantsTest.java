package com.movo.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that all shared constant interfaces expose the expected values.
 * These tests act as a safety-net against accidental value changes.
 */
class CommonConstantsTest {

    // =========================================================
    // FieldConstant
    // =========================================================
    @Nested
    @DisplayName("FieldConstant")
    class FieldConstantTests {

        @Test
        @DisplayName("should expose all expected JSON field names")
        void shouldExposeAllFieldNames() {
            assertThat(FieldConstant.ID).isEqualTo("id");
            assertThat(FieldConstant.MERCHANT).isEqualTo("merchant");
            assertThat(FieldConstant.SAGA_ID).isEqualTo("sagaId");
            assertThat(FieldConstant.USER_ID).isEqualTo("userId");
            assertThat(FieldConstant.AMOUNT).isEqualTo("amount");
            assertThat(FieldConstant.CURRENCY).isEqualTo("currency");
            assertThat(FieldConstant.TYPE).isEqualTo("type");
            assertThat(FieldConstant.SOURCE).isEqualTo("source");
            assertThat(FieldConstant.CATEGORY).isEqualTo("category");
            assertThat(FieldConstant.STATUS).isEqualTo("status");
            assertThat(FieldConstant.FINGERPRINT).isEqualTo("fingerprint");
            assertThat(FieldConstant.CARD_ID).isEqualTo("cardId");
            assertThat(FieldConstant.NOTES).isEqualTo("notes");
            assertThat(FieldConstant.CONFIRMED_BY_USER).isEqualTo("confirmedByUser");
            assertThat(FieldConstant.CONFIDENCE_SCORE).isEqualTo("confidenceScore");
            assertThat(FieldConstant.TXN_TIME).isEqualTo("txnTime");
            assertThat(FieldConstant.CREATED_AT).isEqualTo("createdAt");
            assertThat(FieldConstant.UPDATED_AT).isEqualTo("updatedAt");
            assertThat(FieldConstant.ACCOUNT_ID).isEqualTo("accountId");
        }
    }

    // =========================================================
    // EntityConstant
    // =========================================================
    @Nested
    @DisplayName("EntityConstant")
    class EntityConstantTests {

        @Test
        @DisplayName("should expose the transactions table name")
        void shouldExposeTableName() {
            assertThat(EntityConstant.TABLE_TRANSACTIONS).isEqualTo("transactions");
        }

        @Test
        @DisplayName("should expose all database column names using snake_case")
        void shouldExposeColumnNames() {
            assertThat(EntityConstant.COL_ID).isEqualTo("id");
            assertThat(EntityConstant.COL_SAGA_ID).isEqualTo("saga_id");
            assertThat(EntityConstant.COL_USER_ID).isEqualTo("user_id");
            assertThat(EntityConstant.COL_AMOUNT).isEqualTo("amount");
            assertThat(EntityConstant.COL_CURRENCY).isEqualTo("currency");
            assertThat(EntityConstant.COL_TYPE).isEqualTo("type");
            assertThat(EntityConstant.COL_CATEGORY).isEqualTo("category");
            assertThat(EntityConstant.COL_MERCHANT).isEqualTo("merchant");
            assertThat(EntityConstant.COL_SOURCE).isEqualTo("source");
            assertThat(EntityConstant.COL_STATUS).isEqualTo("status");
            assertThat(EntityConstant.COL_FINGERPRINT).isEqualTo("fingerprint");
            assertThat(EntityConstant.COL_CARD_ID).isEqualTo("card_id");
            assertThat(EntityConstant.COL_ACCOUNT_ID).isEqualTo("account_id");
            assertThat(EntityConstant.COL_NOTES).isEqualTo("notes");
            assertThat(EntityConstant.COL_CONFIRMED_BY_USER).isEqualTo("confirmed_by_user");
            assertThat(EntityConstant.COL_CONFIDENCE_SCORE).isEqualTo("confidence_score");
            assertThat(EntityConstant.COL_TXN_TIME).isEqualTo("txn_time");
            assertThat(EntityConstant.COL_CREATED_AT).isEqualTo("created_at");
            assertThat(EntityConstant.COL_UPDATED_AT).isEqualTo("updated_at");
        }
    }

    // =========================================================
    // EventConstant
    // =========================================================
    @Nested
    @DisplayName("EventConstant")
    class EventConstantTests {

        @Test
        @DisplayName("should expose all event JSON property names")
        void shouldExposeEventFieldNames() {
            assertThat(EventConstant.EVENT_ID).isEqualTo("eventId");
            assertThat(EventConstant.AGGREGATE_ID).isEqualTo("aggregateId");
            assertThat(EventConstant.SAGA_ID).isEqualTo("sagaId");
            assertThat(EventConstant.USER_ID).isEqualTo("userId");
            assertThat(EventConstant.OCCURRED_AT).isEqualTo("occurredAt");
            assertThat(EventConstant.AMOUNT).isEqualTo("amount");
        }
    }

    // =========================================================
    // SecurityConstant
    // =========================================================
    @Nested
    @DisplayName("SecurityConstant")
    class SecurityConstantTests {

        @Test
        @DisplayName("should expose the user-id header name")
        void shouldExposeUserIdHeader() {
            assertThat(SecurityConstant.USER_ID_HEADER).isEqualTo("X-User-Id");
        }

        @Test
        @DisplayName("should expose the default dev user UUID")
        void shouldExposeDefaultDevUserId() {
            assertThat(SecurityConstant.DEFAULT_DEV_USER_ID)
                    .isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        }
    }

    // =========================================================
    // MoneyConstant
    // =========================================================
    @Nested
    @DisplayName("MoneyConstant")
    class MoneyConstantTests {

        @Test
        @DisplayName("should expose the display scale as 2")
        void shouldExposeDisplayScale() {
            assertThat(MoneyConstant.DISPLAY_SCALE).isEqualTo(2);
        }

        @Test
        @DisplayName("should use banker's rounding (HALF_EVEN)")
        void shouldUseHalfEvenRounding() {
            assertThat(MoneyConstant.FINANCIAL_ROUNDING).isEqualTo(RoundingMode.HALF_EVEN);
        }

        @Test
        @DisplayName("should use DECIMAL128 math context")
        void shouldUseDecimal128Context() {
            assertThat(MoneyConstant.COMPUTATION_CONTEXT).isEqualTo(MathContext.DECIMAL128);
        }
    }
}
