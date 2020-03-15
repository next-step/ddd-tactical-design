package kitchenpos.menus.tobe.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {

    @DisplayName("메뉴제품을 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidPrice")
    void create(final BigDecimal price) {
        // given
        final Long productId = 1L;
        final Long quantity = 1L;

        // when
        final MenuProduct menuProduct = new MenuProduct(productId, price, quantity);

        // then
        assertThat(menuProduct.getProductId()).isEqualTo(productId);
        assertThat(menuProduct.getPrice()).isEqualTo(price);
        assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
    }

    private static Stream provideValidPrice() {
        return Stream.of(
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1000000000)
        );
    }

    @DisplayName("메뉴제품 생성 시, 제품이 지정되어야한다.")
    @Test
    void createFailsWhenProductIdIsNull() {
        // given
        final Long productId = null;
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long quantity = 1L;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuProduct(productId, price, quantity);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴제품 생성 시, 제품가격은 0원 이상이여야합니다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createFailsWhenPriceIsNegative(BigDecimal price) {
        // given
        final Long productId = 1L;
        final Long quantity = 1L;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuProduct(productId, price, quantity);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }

    @DisplayName("메뉴제품 생성 시, 제품 개수는 1개 이상이여야합니다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1, -1000})
    void createFailsWhenQuantityIsLessThan1(final Long quantity) {
        // given
        final Long productId = 1L;
        final BigDecimal price = BigDecimal.TEN;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuProduct(productId, price, quantity);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
