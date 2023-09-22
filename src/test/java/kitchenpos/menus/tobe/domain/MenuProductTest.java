package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.domain.Quantity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProductTest {
    @Test
    void MenuProduct는_productId와_Quantity를_갖는다() {;
        assertDoesNotThrow(() -> new MenuProduct(UUID.randomUUID(), 0, 1000L));
    }

    @Test
    void MenuProduct_동등성_비교() {
        UUID productId = UUID.randomUUID();

        MenuProduct actual = new MenuProduct(productId, 0, 1000L);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new MenuProduct(productId, 0, 1000L))).isTrue();
        assertThat(actual.equals(new MenuProduct(productId, 1, 1000L))).isFalse();
        assertThat(actual.equals(new MenuProduct(UUID.randomUUID(), 0, 1000L))).isFalse();
    }

    @Test
    void MenuProduct_hashCode() {
        UUID productId = UUID.randomUUID();

        MenuProduct actual = new MenuProduct(productId, 0, 1000L);

        assertThat(actual.hashCode()).isEqualTo(new MenuProduct(productId, 0, 1000L).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new MenuProduct(productId, 1, 1000L).hashCode());
    }
}
