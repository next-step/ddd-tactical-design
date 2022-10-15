package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;

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

    @Test
    void 메뉴_생성() {
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final Price price = new Price(20000);
        final Long menuGroupId = 1L;

        assertDoesNotThrow(() -> new Menu(name, price, menuGroupId));
    }

    @Test
    void 메뉴의_가격은_0원_이상() {
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final int price = -20000;
        final Long menuGroupId = 1L;

        assertThrows(IllegalArgumentException.class, () -> new Menu(name, price, menuGroupId));
    }

    @org.junit.jupiter.api.DisplayName("")
    public void price() {

    }

    @Test
    @org.junit.jupiter.api.DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    void name() {

    }
}
