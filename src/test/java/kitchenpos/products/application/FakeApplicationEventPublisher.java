package kitchenpos.products.application;

import kitchenpos.menus.application.menu.MenuService;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.domain.menu.ProductClient;
import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    private final MenuService menuService;

    public FakeApplicationEventPublisher(MenuRepository menuRepository, ProductClient productClient) {
        this.menuService = new MenuService(menuRepository, null,null, productClient);
    }

    @Override
    public void publishEvent(Object event) {
        if (event instanceof ProductChangePriceEvent) {
            menuService.changeDisplayed(((ProductChangePriceEvent) event).getProductId());
        }
    }
}
