package kitchenpos.products.tobe.application.adapter;

import java.util.UUID;

import org.springframework.stereotype.Service;

import kitchenpos.menus.tobe.application.MenuService;

@Service
public class ProductsMenuServiceAdapter implements MenuServiceAdapter {
    private final MenuService menuService;

    public ProductsMenuServiceAdapter(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void hideMenusBasedOnProductPrice(UUID productId) {
        menuService.hideMenusBasedOnProductPrice(productId);
    }
}
