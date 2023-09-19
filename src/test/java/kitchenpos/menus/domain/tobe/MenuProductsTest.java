package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProducts;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {
    @Test
    void totalAmount() {
        final List<MenuProduct> products = List.of(new MenuProduct(UUID.randomUUID(), 10_000, 3),
                new MenuProduct(UUID.randomUUID(), 15_000, 2)
        );
        final MenuProducts menuProducts = new MenuProducts(products);
        assertThat(menuProducts.totalAmount()).isEqualTo(60_000);
    }
}