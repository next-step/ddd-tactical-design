package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("Moeny는")
class MoneyTest {

    @DisplayName("만들 수 있다.")
    @Nested
    class 만들_수_있다 {

        @DisplayName("비어 있다면 만들 수 없다.")
        @ParameterizedTest
        @NullSource
        void 비어_있다면_만들_수_없다(BigDecimal value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new Money(value));
        }

        @DisplayName("비어 있지 않다면 만들 수 있다.")
        @Test
        void 비어_있지_않다면_만들_수_있다() {
            assertDoesNotThrow(() -> new Money(BigDecimal.ZERO));
        }
    }

    @DisplayName("비교할 수 있다")
    @Nested
    class 비교할_수_있다 {
        private final Money one = new Money(1);

        @DisplayName("작은 경우")
        @Test
        void 작은_경우() {
            assertAll(
                () -> assertThat(Money.ZERO.isLessThan(one)).isTrue(),
                () -> assertThat(Money.ZERO.isLessThan(Money.ZERO)).isFalse()
            );
        }

        @DisplayName("큰 경우")
        @Test
        void 큰_경우() {
            assertAll(
                () -> assertThat(one.isMoreThan(Money.ZERO)).isTrue(),
                () -> assertThat(one.isMoreThan(one)).isFalse()
            );
        }
    }

    @DisplayName("더할 수 있다")
    @Test
    void 더할_수_있다() {
        final Money 백 = new Money(100);
        final Money 이백 = new Money(200);
        assertThat(백.plus(이백)).isEqualTo(new Money(300));
    }

    @DisplayName("곱할 수 있다")
    @Test
    void 곱할_수_있다() {
        final Money 백 = new Money(100);
        assertThat(백.times(3)).isEqualTo(new Money(300));
    }
}
