package kitchenpos.core.shared.value;

import kitchenpos.config.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@UnitTest
@DisplayName("[Shared] Money 테스트")
class MoneyTest {

    @Test
    @DisplayName("성공: wons(long)으로 Money 생성")
    void createMoneyFromLong() {
        Money money = Money.wons(1000);
        assertNotNull(money);
        assertEquals(BigDecimal.valueOf(1000), money.getAmount());
    }

    @Test
    @DisplayName("성공: wons(BigDecimal)으로 Money 생성")
    void createMoneyFromBigDecimal() {
        Money money = Money.wons(BigDecimal.valueOf(5000));
        assertNotNull(money);
        assertEquals(BigDecimal.valueOf(5000), money.getAmount());
    }

    @Test
    @DisplayName("실패: null 금액으로 Money 생성 시 IllegalArgumentException 예외 발생")
    void createMoneyFailWhenNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Money.wons((BigDecimal) null)
        );
        assertTrue(exception.getMessage().contains("금액은 null이 될 수 없습니다."));
    }

    @Test
    @DisplayName("실패: 음수 금액으로 Money 생성 시 IllegalArgumentException 예외 발생")
    void createMoneyFailWhenNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Money.wons(-100L)
        );
        assertTrue(exception.getMessage().contains("금액은 0보다 작을 수 없습니다."));
    }

    @Test
    @DisplayName("성공: Money.ZERO는 0원이어야 함")
    void moneyZeroTest() {
        Money zero = Money.ZERO;
        assertNotNull(zero);
        assertEquals(BigDecimal.ZERO, zero.getAmount());
    }

    @Test
    @DisplayName("성공: add 연산 테스트")
    void testAdd() {
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(2000);
        Money result = money1.add(money2);
        Money expected = Money.wons(3000);
        assertEquals(expected.getAmount(), result.getAmount());
    }

    @Test
    @DisplayName("성공: multiply 연산 테스트")
    void testMultiply() {
        Money money = Money.wons(500);
        Money result = money.multiply(4);
        Money expected = Money.wons(2000);
        assertEquals(expected.getAmount(), result.getAmount());
    }

    @Test
    @DisplayName("성공: isLessThan 연산 테스트")
    void testIsLessThan() {
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(2000);
        assertTrue(money1.isLessThan(money2));
        assertFalse(money2.isLessThan(money1));
    }

    @Test
    @DisplayName("성공: isBiggerThan 연산 테스트")
    void testIsBiggerThan() {
        Money money1 = Money.wons(3000);
        Money money2 = Money.wons(2000);
        assertTrue(money1.isBiggerThan(money2));
        assertFalse(money2.isBiggerThan(money1));
    }

    @Test
    @DisplayName("성공: isEqual 연산 테스트")
    void testIsEqual() {
        Money money1 = Money.wons(1500);
        Money money2 = Money.wons(1500);
        Money money3 = Money.wons(2000);
        assertTrue(money1.isEqual(money2));
        assertFalse(money1.isEqual(money3));
    }

    @Test
    @DisplayName("성공: toString 테스트")
    void testToString() {
        Money money = Money.wons(2500);
        assertEquals("2500원", money.toString());
    }
}