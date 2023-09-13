package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.menus.domain.tobe.domain.MenuProductPrice;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;

class ToBeMenuProductTest {

    @DisplayName("상품정보가 Null 일수 없다.")
    @ParameterizedTest
    @NullSource
    void name(UUID product) {
        assertThatThrownBy(() -> new ToBeMenuProduct(product, 8000L, 3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProduct의 가격을 조회하면 등록했을때 가격이 조회된다.")
    @Test
    void priceCheck() {
        ToBeMenuProduct menuProduct = new ToBeMenuProduct(UUID.randomUUID(), 10_000L, 3);
        assertThat(menuProduct.amount()).isEqualTo(MenuProductPrice.of(30_000L));
    }

    @DisplayName("메뉴상품 가격을 변경한다")
    @Test
    void changePrice() {
        ToBeMenuProduct menuProduct = new ToBeMenuProduct(UUID.randomUUID(), 10_000L, 3);
        menuProduct.changePrice(BigDecimal.valueOf(15_000));
        assertThat(menuProduct.amount()).isEqualTo(MenuProductPrice.of(45_000L));
    }
}
