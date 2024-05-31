package kitchenpos.products.infra;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.application.MenuServiceClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MenuServiceClientImpl implements MenuServiceClient {
    private final MenuService menuService;

    public MenuServiceClientImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void hideMenuBasedOnProductPrice(UUID productId, BigDecimal productPrice) {
        menuService.hideMenuBasedOnProductPrice(productId, productPrice);
    }
}
