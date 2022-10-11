package kitchenpos.menus.application;

import kitchenpos.event.ProductPriceChangedEvent;
import kitchenpos.menus.domain.MenuProductPrice;
import kitchenpos.menus.domain.MenuProductRepository;
import kitchenpos.menus.domain.MenuProducts;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuEventHandler {

    private final MenuProductRepository menuProductRepository;

    public MenuEventHandler(MenuProductRepository menuProductRepository) {
        this.menuProductRepository = menuProductRepository;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAndCheckPrice(ProductPriceChangedEvent event) {
        MenuProducts menuProducts = new MenuProducts(menuProductRepository.findAllByProductId(event.getProductId()));
        menuProducts.updatePrice(new MenuProductPrice(event.getProductPrice()));
    }
}
