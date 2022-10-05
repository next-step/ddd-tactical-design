package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void create() {
        final UUID productId = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(15_000L), Quantity.valueOf(1L));

        assertAll(
                () -> assertThat(menuProduct.productId()).isEqualTo(productId),
                () -> assertThat(menuProduct.price()).isEqualTo(Price.valueOf(15_000L)),
                () -> assertThat(menuProduct.quantity()).isEqualTo(Quantity.valueOf(1L))
        );
    }

    @DisplayName("금액은 가격 X 수량의 값이다.")
    @Test
    void amount() {
        final UUID productId = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(15_000L), Quantity.valueOf(3L));

        assertThat(menuProduct.amount()).isEqualTo(Price.valueOf(45_000L));
    }

    @DisplayName("가격을 변경한다.")
    @Test
    void changePrice() {
        final UUID productId = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(15_000L), Quantity.valueOf(3L));

        menuProduct.changePrice(Price.valueOf(20_000L));

        assertThat(menuProduct.price()).isEqualTo(Price.valueOf(20_000L));
    }
}
