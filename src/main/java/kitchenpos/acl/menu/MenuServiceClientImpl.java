package kitchenpos.acl.menu;

import kitchenpos.menus.application.MenuService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuServiceClientImpl implements MenuServiceClient {
    private final MenuService menuService;

    public MenuServiceClientImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void hideMenuBasedOnProductPrice(UUID productId) {
        menuService.hideMenuBasedOnProductPrice(productId);
    }
}
