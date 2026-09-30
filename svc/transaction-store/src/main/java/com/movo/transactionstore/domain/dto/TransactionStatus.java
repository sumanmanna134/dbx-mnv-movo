package com.movo.transactionstore.domain.dto;

import java.util.Set;

public enum TransactionStatus {
    PENDING_SETTLEMENT,
    POSTED,
    CONFIRMED,
    CORRECTED,
    REVERSING,
    REVERSED,
    IGNORED;

    public boolean canTransitionTo(TransactionStatus target) {
        return switch (this) {
            case PENDING_SETTLEMENT -> Set.of(POSTED, CONFIRMED, CORRECTED, REVERSING, IGNORED).contains(target);
            case POSTED -> Set.of(CONFIRMED, CORRECTED, REVERSING).contains(target);
            case CONFIRMED -> Set.of(CORRECTED, REVERSING).contains(target);
            case CORRECTED -> Set.of(CONFIRMED, REVERSING).contains(target);
            case REVERSING -> Set.of(REVERSED).contains(target);
            case REVERSED, IGNORED -> false;
        };
    }

}
