package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.fixture.MenuProductsFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuProductsTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(MenuProductsFixture::MENU_PRODUCTS_FIXTURE);
    }

    @DisplayName("비정상적 생성시 에러 검증 - 등록되지 않은 Product 가 포함")
    @Test
    void invalidCreate() {
        assertThatThrownBy(MenuProductsFixture::MENU_PRODUCTS_FIXTURE_WITH_NOT_REGISTERED_PRODUCT)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격 총합")
    @Test
    void getAmountTest() {
        MenuProducts menuProducts = MenuProductsFixture.MENU_PRODUCTS_FIXTURE_WITH_PRICE_AND_QUANTITY(10000L, 2L);
        org.assertj.core.api.Assertions.assertThat(menuProducts.calculateSum())
                .isEqualTo(BigDecimal.valueOf(20000L));
    }


}
