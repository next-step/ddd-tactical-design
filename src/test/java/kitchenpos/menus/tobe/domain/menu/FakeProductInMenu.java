package kitchenpos.menus.tobe.domain.menu;

import java.util.UUID;

public class FakeProductInMenu {

    public static ProductInMenu createFake(UUID productId) {
        return new ProductInMenu(productId);
    }

}
