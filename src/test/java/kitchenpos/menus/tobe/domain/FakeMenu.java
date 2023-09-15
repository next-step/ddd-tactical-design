package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FakeMenu extends Menu {

        public static Menu createFake(String name, PurgomalumClient purgomalumClient, long price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
            MenuPrice menuPrice = new MenuPrice();
            menuPrice.value = BigDecimal.valueOf(price);

            return new Menu(
                    UUID.randomUUID(),
                    MenuName.create(name, purgomalumClient),
                    menuPrice,
                    menuGroup,
                    MenuDisplay.create(displayed),
                    menuProducts
            );
        }

}
