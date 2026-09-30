package com.movo.transactionstore.domain.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionStatusTest {
    @ParameterizedTest
    @MethodSource("allowedTransitions")
    void shouldAllowTransitions(TransactionStatus source, TransactionStatus target) {
        boolean result = source.canTransitionTo(target);
        assertTrue(result, ()-> "Expected "+ source + " to be allowed to transition to " + target);

    }

    @ParameterizedTest
    @MethodSource("invalidTransitions")
    void shouldNotAllowTransitions(TransactionStatus source, TransactionStatus target) {
        boolean result = source.canTransitionTo(target);
        assertFalse(result, ()-> "Expected "+ source + " to not be allowed to transition to " + target);
    }

    // Test Data
    private static Stream<Arguments> allowedTransitions() {
        return Stream.of(
                /* * PENDING_SETTLEMENT can move to POSTED */
                Arguments.of( TransactionStatus.PENDING_SETTLEMENT, TransactionStatus.POSTED ),
                /* * PENDING_SETTLEMENT can move to CONFIRMED */
                Arguments.of( TransactionStatus.PENDING_SETTLEMENT, TransactionStatus.CONFIRMED ),
                /* * PENDING_SETTLEMENT can move to CORRECTED */
                Arguments.of( TransactionStatus.PENDING_SETTLEMENT, TransactionStatus.CORRECTED ),
                /* * PENDING_SETTLEMENT can move to REVERSING */
                Arguments.of( TransactionStatus.PENDING_SETTLEMENT, TransactionStatus.REVERSING ),
                /* * PENDING_SETTLEMENT can move to IGNORED */
                Arguments.of( TransactionStatus.PENDING_SETTLEMENT, TransactionStatus.IGNORED ),
                /* * POSTED can move to CONFIRMED */
                Arguments.of( TransactionStatus.POSTED, TransactionStatus.CONFIRMED ),
                /* * POSTED can move to CORRECTED */
                Arguments.of( TransactionStatus.POSTED, TransactionStatus.CORRECTED ),
                /* * POSTED can move to REVERSING */
                Arguments.of( TransactionStatus.POSTED, TransactionStatus.REVERSING ),
                /* * CONFIRMED can move to CORRECTED */
                Arguments.of( TransactionStatus.CONFIRMED, TransactionStatus.CORRECTED ),
                /* * CONFIRMED can move to REVERSING */
                Arguments.of( TransactionStatus.CONFIRMED, TransactionStatus.REVERSING ),
                /* * CORRECTED can move back to CONFIRMED */
                Arguments.of( TransactionStatus.CORRECTED, TransactionStatus.CONFIRMED ),
                /* * CORRECTED can move to REVERSING */
                Arguments.of( TransactionStatus.CORRECTED, TransactionStatus.REVERSING ),
                /* * REVERSING can finally move to REVERSED */
                Arguments.of( TransactionStatus.REVERSING, TransactionStatus.REVERSED )
        );
    }

    private static Stream<Arguments> invalidTransitions() {
        return Stream.of(
                Arguments.of(TransactionStatus.POSTED, TransactionStatus.PENDING_SETTLEMENT),
                Arguments.of(TransactionStatus.POSTED, TransactionStatus.REVERSED),
                Arguments.of(TransactionStatus.POSTED, TransactionStatus.IGNORED),

                Arguments.of(TransactionStatus.CONFIRMED, TransactionStatus.PENDING_SETTLEMENT),
                Arguments.of(TransactionStatus.CONFIRMED, TransactionStatus.POSTED),
                Arguments.of(TransactionStatus.CONFIRMED, TransactionStatus.REVERSED),

                Arguments.of(TransactionStatus.CORRECTED, TransactionStatus.PENDING_SETTLEMENT),
                Arguments.of(TransactionStatus.CORRECTED, TransactionStatus.POSTED),
                Arguments.of(TransactionStatus.CORRECTED, TransactionStatus.REVERSED),


                Arguments.of(TransactionStatus.REVERSING, TransactionStatus.PENDING_SETTLEMENT),
                Arguments.of(TransactionStatus.REVERSING, TransactionStatus.POSTED),
                Arguments.of(TransactionStatus.REVERSING, TransactionStatus.CONFIRMED),
                Arguments.of(TransactionStatus.REVERSING, TransactionStatus.CORRECTED)

        );
    }


}