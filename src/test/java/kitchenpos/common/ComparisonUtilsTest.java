package kitchenpos.common;

import kitchenpos.common.util.ComparisonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparisonUtilsTest {

    private BigDecimal TWO;
    private BigDecimal ONE;

    @BeforeEach
    void setUp() {
        TWO = BigDecimal.valueOf(2);
        ONE = BigDecimal.ONE;
    }

    @DisplayName("두 파라미터의 값이 같은 경우 true, 다른 경우 false를 반환합니다.")
    @Test
    void isEqual() {
        assertTrue(ComparisonUtils.isEqual(ONE, ONE));
        assertFalse(ComparisonUtils.isEqual(ONE, TWO));
    }

    @DisplayName("1번째 파라미터가 2번째 파라미터 보다 같거나 큰 경우 true, 아닌 경우 false를 반환합니다.")
    @Test
    void greaterOrEqual() {
        assertTrue(ComparisonUtils.greaterOrEqual(ONE, ONE));
        assertTrue(ComparisonUtils.greaterOrEqual(TWO, ONE));
        assertFalse(ComparisonUtils.greaterOrEqual(ONE, TWO));
    }

    @DisplayName("1번째 파라미터가 2번째 파라미터 보다 큰 경우 true, 아닌 경우 false를 반환합니다.")
    @Test
    void greaterThan() {
        assertFalse(ComparisonUtils.greaterThan(ONE, ONE));
        assertTrue(ComparisonUtils.greaterThan(TWO, ONE));
        assertFalse(ComparisonUtils.greaterThan(ONE, TWO));
    }

    @DisplayName("1번째 파라미터가 2번째 파라미터 보다 작거나 같은 경우 true, 아닌 경우 false를 반환합니다.")
    @Test
    void lessOrEqual() {
        assertTrue(ComparisonUtils.lessOrEqual(ONE, ONE));
        assertFalse(ComparisonUtils.lessOrEqual(TWO, ONE));
        assertTrue(ComparisonUtils.lessOrEqual(ONE, TWO));
    }

    @DisplayName("1번째 파라미터가 2번째 파라미터 보다 작은 경우 true, 아닌 경우 false를 반환합니다.")
    @Test
    void lessThan() {
        assertFalse(ComparisonUtils.lessThan(ONE, ONE));
        assertFalse(ComparisonUtils.lessThan(TWO, ONE));
        assertTrue(ComparisonUtils.lessThan(ONE, TWO));
    }

}
