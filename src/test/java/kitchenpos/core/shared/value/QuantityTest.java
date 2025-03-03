package kitchenpos.core.shared.value;

import kitchenpos.config.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("[Shared] Quantity 테스트")
class QuantityTest {

    @Test
    @DisplayName("성공: 유효한 수량으로 Quantity 생성")
    void createQuantitySuccess() {
        // given
        long validValue = 5;
        // when
        Quantity quantity = Quantity.of(validValue);
        // then
        assertNotNull(quantity);
        assertEquals(validValue, quantity.getValue());
    }

    @Test
    @DisplayName("실패: 음수 수량으로 Quantity 생성 시 IllegalArgumentException 예외 발생")
    void createQuantityFailWhenNegative() {
        // given
        long negativeValue = -1;
        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Quantity.of(negativeValue)
        );
        assertTrue(exception.getMessage().contains("수량은 음수가 될 수 없습니다."));
    }

    @Test
    @DisplayName("성공: toString 테스트")
    void testToString() {
        // given
        Quantity quantity = Quantity.of(10);
        // when
        String result = quantity.toString();
        // then
        assertEquals("10 개", result);
    }

    @Test
    @DisplayName("성공: isEqual 테스트")
    void testEquality() {
        // given
        Quantity q1 = Quantity.of(5);
        Quantity q2 = Quantity.of(5);
        Quantity q3 = Quantity.of(6);
        // then
        assertEquals(q1, q2);
        assertNotEquals(q1, q3);
    }
}