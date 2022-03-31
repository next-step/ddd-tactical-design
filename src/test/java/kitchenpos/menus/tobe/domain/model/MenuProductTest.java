package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {

    @DisplayName("메뉴 상품(Menu Product) 는 식별자와 상품(Product), 수량을 가진다. ")
    @Test
    void create() {
        MenuProduct menuProduct = new MenuProduct(
                new Price(BigDecimal.valueOf(1000L)),
                2,
                UUID.randomUUID()
        );
        assertThat(menuProduct).isNotNull();
    }

    @DisplayName("메뉴 상품(Menu Product)의 가격은 상품의 가격 * 수량이다.")
    @Test
    void getTotalPrice() {
        MenuProduct menuProduct = new MenuProduct(
                new Price(BigDecimal.valueOf(1000L)),
                2,
                UUID.randomUUID()
        );
        assertThat(menuProduct.getSubTotalPrice()).isEqualTo(new Price(BigDecimal.valueOf(2000L)));
    }

}
