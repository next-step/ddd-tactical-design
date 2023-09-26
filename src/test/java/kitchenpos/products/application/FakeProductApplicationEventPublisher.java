package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeProductApplicationEventPublisher implements ApplicationEventPublisher {
    private final MenuService menuService;

    public FakeProductApplicationEventPublisher(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void publishEvent(Object event) {
        if (event instanceof ProductPriceChangeEvent) {
            ProductPriceChangeEvent productPriceChangeEvent = (ProductPriceChangeEvent) event;
            menuService.changeProductPrice(productPriceChangeEvent.getProductId(), productPriceChangeEvent.getPrice());
        }

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

}
