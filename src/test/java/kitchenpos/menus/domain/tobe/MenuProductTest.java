package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductTest {

    @DisplayName("MenuProduct는 식별자와 Product 식별자와 수량을 가진다.")
    @Test
    void create() {
        final Long givenMenuId = 1L;
        final long givenQuantity = 3l;
        final MenuProduct menuProduct = new MenuProduct(givenMenuId, givenQuantity, UUID.randomUUID());

        assertAll(
                () -> assertThat(menuProduct).extracting("seq").isNotNull(),
                () -> assertThat(menuProduct).extracting("quantity").isNotNull(),
                () -> assertThat(menuProduct).extracting("productId").isNotNull()
        );
    }

    @DisplayName("MenuProduct는 수량은 음수가 될 수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -5, -10})
    void create_with_negative_quantity(long quantity) {
        assertThatCode(() -> new MenuProduct(1L, quantity, UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
