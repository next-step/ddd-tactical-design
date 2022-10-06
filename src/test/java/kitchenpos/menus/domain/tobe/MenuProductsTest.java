package kitchenpos.menus.domain.tobe;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuProductsTest {

    @Test
    @DisplayName("메뉴 상품 묶음 생성이 가능하다")
    void constructor() {
        final MenuProducts menuProducts = new MenuProducts(List.of(new MenuProduct(1L, product(), 1)));
        assertThat(menuProducts).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품은 필수이다")
    @NullAndEmptySource
    void constructor_with_null_and_empty(final List<MenuProduct> menuProducts) {
        assertThatThrownBy(() -> new MenuProducts(menuProducts))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품들의 총합 계산이 가능하다")
    @MethodSource("provideMenuProductsAndTotalPrice")
    void calculate_totalPrice(final List<MenuProduct> menuProducts, final BigDecimal price) {
        final MenuProducts expected = new MenuProducts(menuProducts);
        assertThat(expected.totalPrice()).isEqualTo(price);
    }

    private static Stream<Arguments> provideMenuProductsAndTotalPrice() {
        return Stream.of(
            Arguments.of(List.of(new MenuProduct(1L, product("오원", 5L), 5L)), BigDecimal.valueOf(25)),
            Arguments.of(List.of(new MenuProduct(1L, product("오원", 5L), 5L), new MenuProduct(1L, product("천원", 1000L), 5L)), BigDecimal.valueOf(5025))
        );
    }
}
