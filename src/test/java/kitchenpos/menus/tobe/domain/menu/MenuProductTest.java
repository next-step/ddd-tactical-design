package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {

    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
    @Test
    void validateQuantity() {
        assertThatThrownBy(() -> new MenuProduct(new Product(UUID.randomUUID(), new Name("후라이드 치킨", false), new Price(BigDecimal.valueOf(8000))), new Quantity(BigDecimal.ZERO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품의 수량은 0이상 이어야 합니다.");
    }

}
