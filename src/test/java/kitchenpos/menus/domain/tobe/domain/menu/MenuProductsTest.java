package kitchenpos.menus.domain.tobe.domain.menu;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kitchenpos.ToBeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("메뉴 상품 일급 컬렉션을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> ToBeFixtures.menuProducts()
        );
    }
}
