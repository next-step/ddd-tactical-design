package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.domain.tobe.MenuProduct;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {
    @Test
    void amount() {
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 10000, 3);
        final int amount = menuProduct.amount();
        assertThat(amount).isEqualTo(30_000);
    }

}