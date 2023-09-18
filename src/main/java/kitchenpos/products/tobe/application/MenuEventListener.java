package kitchenpos.products.tobe.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.tobe.dto.ProductChangePriceRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MenuEventListener {
    private final MenuService menuService;

    public MenuEventListener(MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    public void handleMenuPriceChange(MenuPriceChangeEvent event) {
        UUID menuId = event.getMenuId();
        BigDecimal newPrice = event.getNewPrice();

        menuService.changePrice(menuId, new ProductChangePriceRequest(newPrice));
    }
}
