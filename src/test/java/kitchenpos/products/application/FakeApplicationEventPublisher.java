package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    private final MenuService menuService;

    public FakeApplicationEventPublisher(ProductRepository productRepository, MenuRepository menuRepository) {
        this.menuService = new MenuService(menuRepository, null, productRepository, null);
    }

    @Override
    public void publishEvent(Object event) {
        if (event instanceof ProductChangePriceEvent) {
            menuService.changeDisplayed(((ProductChangePriceEvent) event).getProductId());
        }
    }
}
