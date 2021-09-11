package kitchenpos.menus.tobe.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("productId와 quantity로 menuProduct를 생성할 수 있다")
    @Test
    void menuProduct() {
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), new Quantity(2L));
        assertThat(menuProduct).isNotNull();
    }

}
