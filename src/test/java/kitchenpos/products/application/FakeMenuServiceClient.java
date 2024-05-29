package kitchenpos.products.application;

import kitchenpos.support.acl.menu.MenuServiceClient;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class FakeMenuServiceClient implements MenuServiceClient {
    private final MenuService menuService;

    public FakeMenuServiceClient(MenuRepository menuRepository) {
        this.menuService = new MenuService(menuRepository, null, null, null);
    }

    @Override
    public void hideMenuBasedOnProductPrice(UUID productId, BigDecimal productPrice) {
        menuService.hideMenuBasedOnProductPrice(productId, productPrice);
    }
}
