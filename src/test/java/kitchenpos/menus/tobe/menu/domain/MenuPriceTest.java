package kitchenpos.menus.tobe.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @DisplayName("제품가격을 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidPrice")
    void create(BigDecimal price) {
        // given
        // when
        final MenuPrice menuPrice = new MenuPrice(price);

        // then
        assertThat(menuPrice.get()).isEqualTo(price);
    }

    private static Stream provideValidPrice() {
        return Stream.of(
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1000000000)
        );
    }

    @DisplayName("제품가격 생성 시, 0원 이상이여야합니다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createFailsWhenPriceIsNegative(BigDecimal price) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new MenuPrice(price);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }
}
