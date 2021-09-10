package kitchenpos.products.tobe.infra;

import kitchenpos.menus.tobe.application.MenuService;

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
