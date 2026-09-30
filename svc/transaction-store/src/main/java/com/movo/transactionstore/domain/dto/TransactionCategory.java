package com.movo.transactionstore.domain.dto;

import java.util.Arrays;

public enum TransactionCategory {
    FOOD,
    DRINK,
    HOUSING,
    TRANSPORT,
    SHOPPING,
    UTILITIES;

    /**
     * Safely resolves a category from its string name (case-insensitive).
     *
     * @throws IllegalArgumentException with a descriptive message listing valid
     *                                  values
     */
    public static TransactionCategory from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction category must not be null or blank. Valid values: "
                            + Arrays.toString(values()));
        }
        try {
            return TransactionCategory.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Unknown transaction category: '" + value + "'. Valid values: "
                            + Arrays.toString(values()));
        }
    }
}
