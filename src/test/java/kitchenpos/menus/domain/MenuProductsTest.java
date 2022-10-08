package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import kitchenpos.menus.exception.InvalidMenuProductsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("메뉴의 상품은 하나 이상이어야 한다.")
    @Test
    void emptyException() {
        assertThatThrownBy(() -> new MenuProducts(new ArrayList<>()))
            .isExactlyInstanceOf(InvalidMenuProductsException.class);
    }
}
