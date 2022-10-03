package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("메뉴상품 테스트")
class MenuProductTest {

    @DisplayName("메뉴상품은 상품식별자와 가격, 수량을 가진다.")
    @Test
    void createMenuProduct() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), Price.from(16_000L), Quantity.from(2));
        assertThat(menuProduct).isNotNull();
    }

    @DisplayName("메뉴에 속한 상품의 가격은 0원 이상이어야 한다.")
    @Test
    void createMenuProduct_NotValidPrice() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuProduct(UUID.randomUUID(), Price.from(16_000L), Quantity.from(-1)));
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
    @Test
    void createMenuProduct_NotValidQuantity() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuProduct(UUID.randomUUID(), Price.from(16_000L), Quantity.from(-1)));
    }
}