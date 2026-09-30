package com.movo.constants;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Monetary computation constants — scale, rounding, and precision
 * used by the Money value object and any financial calculations.
 */
public interface MoneyConstant {
    int DISPLAY_SCALE = 2;
    RoundingMode FINANCIAL_ROUNDING = RoundingMode.HALF_EVEN;
    MathContext COMPUTATION_CONTEXT = MathContext.DECIMAL128;
}
