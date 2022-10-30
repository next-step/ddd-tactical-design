package kitchenpos.menus.tobe.domain.service;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.List;
import java.util.UUID;

public class MenuIncludedProductPricePolicy {
    private final MenuRepository menuRepository;

    public MenuIncludedProductPricePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void whenProductPriceChanged(UUID productId) {
        List<Menu> productIncludedMenus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : productIncludedMenus) {
            if (! menu.isReasonablePrice()) {
                menu.hide();
            }
        }
    }
}
