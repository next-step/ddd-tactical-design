package kitchenpos.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonUtilsTest {

    private BigDecimal TWO;
    private BigDecimal ONE;

    @BeforeEach
    void setUp() {
        TWO = BigDecimal.valueOf(2);
        ONE = BigDecimal.ONE;
    }

    @Test
    void isEqual() {
        assertTrue(ComparisonUtils.isEqual(ONE, ONE));
        assertFalse(ComparisonUtils.isEqual(ONE, TWO));
    }

    @Test
    void greaterOrEqual() {
        assertTrue(ComparisonUtils.greaterOrEqual(ONE, ONE));
        assertTrue(ComparisonUtils.greaterOrEqual(TWO, ONE));
        assertFalse(ComparisonUtils.greaterOrEqual(ONE, TWO));
    }

    @Test
    void greaterThan() {
        assertFalse(ComparisonUtils.greaterThan(ONE, ONE));
        assertTrue(ComparisonUtils.greaterThan(TWO, ONE));
        assertFalse(ComparisonUtils.greaterThan(ONE, TWO));
    }

    @Test
    void lessOrEqual() {
        assertTrue(ComparisonUtils.lessOrEqual(ONE, ONE));
        assertFalse(ComparisonUtils.lessOrEqual(TWO, ONE));
        assertTrue(ComparisonUtils.lessOrEqual(ONE, TWO));
    }

    @Test
    void lessThan() {
        assertFalse(ComparisonUtils.lessThan(ONE, ONE));
        assertFalse(ComparisonUtils.lessThan(TWO, ONE));
        assertTrue(ComparisonUtils.lessThan(ONE, TWO));
    }

}
