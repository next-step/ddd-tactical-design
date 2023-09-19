package kitchenpos.menus.domain.tobe;

//### 메뉴
//
//        - `MenuGroup`은 식별자와 이름을 가진다.
//        - `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
//        - `DisplayedName`에는 `Profanity`가 포함될 수 없다.
//        - `Menu`는 특정 `MenuGroup`에 속한다.
//        - `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
//        - `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
//        - `MenuProduct`는 가격과 수량을 가진다.

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class MenuTest {
    @Test
    void constructor() {
        final DisplayedName name = new DisplayedName("치킨", new FakeProfanities());
        final MenuProducts products = new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(),10_000, 3)));
        Assertions.assertDoesNotThrow(() ->  new Menu(UUID.randomUUID(), name, 30_000, products));
    }

    @Test
    void price() {
        final DisplayedName name = new DisplayedName("치킨", new FakeProfanities());
        final MenuProducts products = new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(),10_000, 3)));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Menu(UUID.randomUUID(), name, 40_000, products));
    }
}