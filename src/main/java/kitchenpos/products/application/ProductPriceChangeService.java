package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductPriceChangeService {

    private final MenuService menuService;


    public ProductPriceChangeService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void changeProductPrice(UUID productId, BigDecimal price) {
        menuService.changeProductPrice(productId, price);
    }
}
