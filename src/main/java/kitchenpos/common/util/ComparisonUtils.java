package kitchenpos.common.util;

import java.math.BigDecimal;

public class ComparisonUtils {
    private ComparisonUtils() {
    }

    public static boolean isEqual(BigDecimal former, BigDecimal latter) {
        return former.compareTo(latter) == 0;
    }

    public static boolean greaterOrEqual(BigDecimal former, BigDecimal latter) {
        return former.compareTo(latter) >= 0;
    }

    public static boolean greaterThan(BigDecimal former, BigDecimal latter) {
        return former.compareTo(latter) > 0;
    }

    public static boolean lessOrEqual(BigDecimal former, BigDecimal latter) {
        return former.compareTo(latter) <= 0;
    }

    public static boolean lessThan(BigDecimal former, BigDecimal latter) {
        return former.compareTo(latter) < 0;
    }

}
