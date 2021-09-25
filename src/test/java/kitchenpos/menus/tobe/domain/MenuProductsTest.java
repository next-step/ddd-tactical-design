package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductsTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Price price = new Price(BigDecimal.valueOf(10000L));
        Quantity quantity = new Quantity(BigDecimal.valueOf(2L));

        MenuProduct menuProduct1 = new MenuProduct(UUID.randomUUID(), price, quantity);
        MenuProduct menuProduct2 = new MenuProduct(UUID.randomUUID(), price, quantity);
        Assertions.assertDoesNotThrow(() -> new MenuProducts(menuProduct1, menuProduct2));
    }

    @DisplayName("비정상적 생성시 에러 검증")
    @Test
    void invalidCreate() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new MenuProducts((MenuProduct) null))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("가격 총합")
    @Test
    void getAmountTest() {
        Price price = new Price(BigDecimal.valueOf(10000L));
        Quantity quantity = new Quantity(BigDecimal.valueOf(2L));

        MenuProduct menuProduct1 = new MenuProduct(UUID.randomUUID(), price, quantity);
        MenuProduct menuProduct2 = new MenuProduct(UUID.randomUUID(), price, quantity);
        MenuProducts menuProducts = new MenuProducts(menuProduct1, menuProduct2);
        org.assertj.core.api.Assertions.assertThat(menuProducts.getPriceSum())
                .isEqualTo(BigDecimal.valueOf(40000L));
    }

}
