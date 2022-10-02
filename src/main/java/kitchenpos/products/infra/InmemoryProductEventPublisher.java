package kitchenpos.products.infra;

import java.util.UUID;
import kitchenpos.menus.application.MenuService;
import org.springframework.stereotype.Component;

@Component
public class InmemoryProductEventPublisher implements ProductEventPublisher {

    private final MenuService menuService;

    public InmemoryProductEventPublisher(final MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void publishChangePriceEvent(UUID productID) {
        menuService.applyProductPrice(productID);
    }
}
