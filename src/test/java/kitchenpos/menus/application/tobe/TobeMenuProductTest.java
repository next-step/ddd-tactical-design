package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.application.tobe.TobeMenusFixtures.*;
import static org.assertj.core.api.Assertions.*;

class TobeMenuProductTest {
    private TobePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new TobeFakePurgomalumClient();
    }

    @DisplayName("메뉴 상품 생성")
    @Test
    void create() {
        TobeMenuProduct menuProduct = menuProduct();

        assertThat(menuProduct).isNotNull();
    }

    @DisplayName("메뉴 상품 개수 확인")
    @Test
    void quantity() {
        TobeMenuProduct menuProduct = menuProduct(5L);

        assertThat(menuProduct.getQuantity()).isEqualTo(5L);
    }

    @DisplayName("메뉴 상품 개수 예외")
    @Test
    void nagetiveQuantity() {
        assertThatThrownBy(
                () -> menuProduct(-5L)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품 금액 확인")
    @Test
    void menuProductPrice() {
        TobeMenuProduct menuProduct = menuProduct(product("후라이드", 16_000L), 5L);

        assertThat(menuProduct.menuProductPrice()).isEqualTo(BigDecimal.valueOf(80_000L));
    }
}