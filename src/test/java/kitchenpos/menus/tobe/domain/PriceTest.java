package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("유효한 가격일 경우 정상적으로 객체가 생성된다.")
        @ParameterizedTest
        @ValueSource(strings = {"0", "1000"})
        void create(BigDecimal value) {
            // when
            Price price = new Price(value);

            // then
            assertThat(price.getValue()).isEqualTo(value);
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    public class FailTest {
        @DisplayName("가격이 null 일 경우 예외가 발생한다.")
        @Test
        void nullPrice() {
            assertThatIllegalArgumentException().isThrownBy(() -> new Price(null));
        }

        @DisplayName("가격이 음수일 경우 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = "-1")
        void negativePrice(BigDecimal value) {
            assertThatIllegalArgumentException().isThrownBy(() -> new Price(value));
        }
    }
}
