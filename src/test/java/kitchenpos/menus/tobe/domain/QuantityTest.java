package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("유효한 수량일 경우 정상적으로 객체가 생성된다.")
        @ParameterizedTest
        @ValueSource(longs = {0, 10})
        void create(long value) {
            // when
            Quantity quantity = new Quantity(value);

            // then
            assertThat(quantity.getValue()).isEqualTo(value);
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    public class FailTest {
        @DisplayName("수량이 0보다 작을 경우 예외가 발생한다.")
        @Test
        void negativeQuantity() {
            assertThatIllegalArgumentException().isThrownBy(() -> new Quantity(-1));
        }
    }
}
