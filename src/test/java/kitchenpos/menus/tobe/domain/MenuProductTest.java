package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProductTest {
    @Test
    void MenuProduct는_productId와_Quantity를_갖는다() {;
        assertDoesNotThrow(() -> new MenuProduct(UUID.randomUUID(), new Quantity(0)));
    }

    @Test
    void MenuProduct_동등성_비교() {
        UUID productId = UUID.randomUUID();
        Quantity zero = new Quantity(0);

        MenuProduct actual = new MenuProduct(productId, zero);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new MenuProduct(productId, zero))).isTrue();
        assertThat(actual.equals(new MenuProduct(productId, new Quantity(1)))).isFalse();
        assertThat(actual.equals(new MenuProduct(UUID.randomUUID(), new Quantity(0)))).isFalse();
    }

    @Test
    void MenuProduct_hashCode() {
        UUID productId = UUID.randomUUID();
        Quantity zero = new Quantity(0);

        MenuProduct actual = new MenuProduct(productId, zero);

        assertThat(actual.hashCode()).isEqualTo(new MenuProduct(productId, zero).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new MenuProduct(productId, new Quantity(1)).hashCode());
    }
}
