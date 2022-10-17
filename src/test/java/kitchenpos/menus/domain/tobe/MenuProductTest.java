package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    @Test
    @DisplayName("메뉴 상품 생성이 가능하다")
    void constructor() {
        final MenuProduct expected = new MenuProduct(1L, UUID.randomUUID(), BigDecimal.ONE, 1L);
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품의 수량은 0 이상이어야한다")
    @ValueSource(longs = { -1 })
    void constructor_with_negative_quantity(final Long value) {
        assertThatThrownBy(() -> new MenuProduct(1L, UUID.randomUUID(), BigDecimal.ONE, value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품에 속하는 상품은 필수이다")
    @NullSource
    void constructor_with_null_product(final UUID id) {
        assertThatThrownBy(() -> new MenuProduct(1L, id, BigDecimal.TEN, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품의 총 가격을 계산할 수 있다")
    @MethodSource("provideProductAndQuantityAndTotalPrice")
    void calculate_totalPrice(final Long productPrice, final Long quantity, final BigDecimal price) {
        final MenuProduct expected = new MenuProduct(1L, UUID.randomUUID(), BigDecimal.valueOf(productPrice), quantity);
        assertThat(expected.totalPrice()).isEqualTo(price);
    }

    private static Stream<Arguments> provideProductAndQuantityAndTotalPrice() {
        return Stream.of(
            Arguments.of( 10L, 5L, BigDecimal.valueOf(50)),
            Arguments.of( 1500L, 5L, BigDecimal.valueOf(7500)),
            Arguments.of( 1L, 1L, BigDecimal.valueOf(1))
        );
    }
}
