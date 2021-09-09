package kitchenpos.common.infra;

import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.products.tobe.domain.ProductTranslator;

import java.util.UUID;

// @Component
public class ServiceProductTranslator implements ProductTranslator {
    private final MenuService menuService;

    public ServiceProductTranslator(final MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void updateMenuStatus(final UUID productId) {
        menuService.updateStatus(productId);
    }
}
