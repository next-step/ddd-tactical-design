package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuEvent;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    private final MenuService menuService;

    public FakeApplicationEventPublisher(ProductRepository productRepository, MenuRepository menuRepository) {
        this.menuService = new MenuService(menuRepository, null, productRepository, null);
    }

    @Override
    public void publishEvent(Object event) {
        if (event instanceof MenuEvent) {
            menuService.changeMenuDisplayStatus(((MenuEvent) event).getProductId());
        }
    }
}
