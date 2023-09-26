package kitchenpos.products.supports;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChange implements ProductPriceChangeSupport {

    private final MenuService menuService;

    public ProductPriceChange(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void changeProductPrice(ProductPriceChangeEvent productPriceChangeEvent) {
        menuService.changeProductPrice(productPriceChangeEvent.getProductId(), productPriceChangeEvent.getPrice());

    }
}
