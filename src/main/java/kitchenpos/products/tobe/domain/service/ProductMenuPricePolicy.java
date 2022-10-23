package kitchenpos.products.tobe.domain.service;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;

import java.util.List;
import java.util.UUID;

public class ProductMenuPricePolicy {
    private final MenuRepository menuRepository;

    public ProductMenuPricePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void checkMenuPrice(UUID productId) {
        List<Menu> productIncludedMenus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : productIncludedMenus) {
            if (! menu.isReasonablePrice()) {
                menu.hide();
            }
        }
    }
}
