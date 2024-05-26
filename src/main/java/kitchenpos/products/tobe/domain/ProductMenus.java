package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;

import java.util.List;

public class ProductMenus {

    public ProductMenus(List<Menu> productMenus) {
        for (final Menu menu : productMenus) {
            menu.validateMenuProduct();
        }
    }
}
