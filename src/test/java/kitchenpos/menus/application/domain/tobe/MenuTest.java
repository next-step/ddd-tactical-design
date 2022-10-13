package kitchenpos.menus.application.domain.tobe;

import kitchenpos.menus.tobe.domain.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    - 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
    - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 가격은 0원 이상이어야 한다.
    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
    - 메뉴는 특정 메뉴 그룹에 속해야 한다.
    - 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 이름에는 비속어가 포함될 수 없다.
 */
public class MenuTest {

    @Test
    void 메뉴_생성() {
        final String name = "치킨";
        final int price = 20000;
        final Long menuGroupId = 1L;

        assertDoesNotThrow(() -> new Menu(name, price, menuGroupId));
    }

    @Test
    void 메뉴의_가격은_0원_이상() {
        final String name = "치킨";
        final int price = -20000;
        final Long menuGroupId = 1L;

        assertThrows(IllegalArgumentException.class, () -> new Menu(name, price, menuGroupId));
    }
}
