package com.mov.money;

public enum Currency {
    INR("₹", 2, "Indian Rupee"),
    USD("$", 2, "US Dollar"),
    EUR("€", 2, "Euro"),
    GBP("£", 2, "British Pound");

    private final String symbol;
    private final int minorUnitScale;
    private final String displayName;

    Currency(String symbol, int minorUnitScale, String displayName) {
        this.symbol = symbol;
        this.minorUnitScale = minorUnitScale;
        this.displayName = displayName;
    }


    public String getSymbol() {
        return symbol;
    }

    /**
     * The number of decimal places used by the smallest currency unit.
     * INR: 2 (₹1 = 100 paise → scale 2)
     * JPY: 0 (no sub-unit → scale 0)
     */
    public int getMinorUnitScale() {
        return minorUnitScale;
    }

    public String getDisplayName() {
        return displayName;
    }
}
