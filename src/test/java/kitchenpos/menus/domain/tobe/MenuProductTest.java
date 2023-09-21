package kitchenpos.menus.domain.tobe;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static kitchenpos.Fixtures.price;
import static kitchenpos.Fixtures.quantity;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {
    @Test
    @DisplayName("메뉴에 속한 상품의 총 가격은 상품의 가격에 수량을 곱한 값이다")
    void amount() {
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), price(10_000), quantity(3));
        final Price amount = menuProduct.amount();
        assertThat(amount).isEqualTo(price(30_000));
    }

}