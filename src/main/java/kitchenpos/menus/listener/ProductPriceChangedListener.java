package kitchenpos.menus.listener;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.service.MenuIncludedProductPricePolicy;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceChangedListener {
    private final MenuRepository menuRepository;

    public ProductPriceChangedListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @EventListener
    public void listen(ProductPriceChangedEvent event) {
        MenuIncludedProductPricePolicy policy = new MenuIncludedProductPricePolicy(menuRepository);

        policy.whenProductPriceChanged(event.getProductId());
    }
}
