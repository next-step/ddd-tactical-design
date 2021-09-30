package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.menu.domain.Quantity;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴 상품의 수량(Quantity)은")
class QuantityTest {

    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final long value = 3000L;
        final Quantity quantity = new Quantity(value);

        assertThat(quantity).isEqualTo(new Quantity(value));
    }

    @ParameterizedTest
    @ValueSource(strings = "-1")
    @DisplayName("0보다 작으면 IllegalArgumentException이 발생한다.")
    void create(long value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Quantity(value);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }
}
