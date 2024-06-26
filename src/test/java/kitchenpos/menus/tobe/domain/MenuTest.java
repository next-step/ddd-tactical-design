package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;


class MenuTest {

    @DisplayName("메뉴 생성")
    @Test
    void createMenu_successTest() {

        Assertions.assertDoesNotThrow(() -> {
            new Menu(UUID.randomUUID(), new Name("메뉴"), Money.from(1000L), new MenuGroup(), true, List.of());
        });
    }

}