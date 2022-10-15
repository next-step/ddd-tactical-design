package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    - 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
    - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 가격은 0원 이상이어야 한다.
    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
    - 메뉴는 특정 메뉴 그룹에 속해야 한다.
 */
public class MenuTest {

    public final FakeProfanities profanities = new FakeProfanities();

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    public void 메뉴_생성() {
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final Price price = new Price(20000);
        final Long menuGroupId = 1L;

        MenuProduct menuProductId1 = new MenuProduct(1L, 10000, 1);
        MenuProduct menuProductId2 = new MenuProduct(2L, 15000, 1);

        assertDoesNotThrow(() -> new Menu(name, price, menuGroupId, List.of(menuProductId1, menuProductId2)));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작으면 예외가 발생한다.")
    @Test
    public void price_exception() {
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final Price price = new Price(20000);
        final Long menuGroupId = 1L;

        MenuProduct menuProductId1 = new MenuProduct(1L, 10000, 1);
        MenuProduct menuProductId2 = new MenuProduct(2L, 5000, 1);

        assertThrows(IllegalArgumentException.class, ()
                -> new Menu(name, price, menuGroupId, List.of(menuProductId1, menuProductId2)));
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void name() {

    }
}
