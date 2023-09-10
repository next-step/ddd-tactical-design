package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;

class ToBeMenuProductTest {

    @DisplayName("상품정보가 Null 일수 없다.")
    @ParameterizedTest
    @NullSource
    void name(ToBeProduct product) {
        assertThatThrownBy(() -> new ToBeMenuProduct(product, 3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProduct의 가격을 조회하면 등록했을때 가격이 조회된다.")
    @Test
    void name() {
        ToBeProduct product = new ToBeProduct("김밥", BigDecimal.valueOf(12_000L), false);
        ToBeMenuProduct menuProduct = new ToBeMenuProduct(product, 3);
        assertThat(menuProduct.getProductPrice().equals(Price.of(12_000L))).isTrue();
    }
}
