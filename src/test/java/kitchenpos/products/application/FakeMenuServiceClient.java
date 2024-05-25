package kitchenpos.products.application;

import kitchenpos.acl.menu.MenuServiceClient;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.MenuRepository;

import java.util.UUID;

public class FakeMenuServiceClient implements MenuServiceClient {
    private final MenuService menuService;

    public FakeMenuServiceClient(MenuRepository menuRepository) {
        this.menuService = new MenuService(menuRepository, null, null, null);
    }

    @Override
    public void hideMenuBasedOnProductPrice(UUID productId) {
        menuService.hideMenuBasedOnProductPrice(productId);
    }
}
