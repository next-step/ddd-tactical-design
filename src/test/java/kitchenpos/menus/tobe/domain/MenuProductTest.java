package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    private final MenuGroup menuGroup = new MenuGroup("추천 메뉴");
    private final Menu menu = new Menu("순대국", BigDecimal.valueOf(7_000L), menuGroup);

    @DisplayName("메뉴 상품의 가격을 계산한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1000", "5000"})
    void calculate(final BigDecimal price) {
        //given
        MenuProduct menuProduct = new MenuProduct(1L, menu, 1L, 2);

        //expect
        assertThat(menuProduct.calculatePrice(price))
            .isEqualTo(price.multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
    }

    @DisplayName("메뉴 상품을 메뉴에 등록한다.")
    @Test
    void registerMenuProductOnMenu() {
        //given
        MenuProduct menuProduct = new MenuProduct(1L, null, 1L, 2);

        //when
        menuProduct.registeredOn(menu);

        //then
        assertThat(menuProduct.getMenu()).isEqualTo(menu);
    }

}
