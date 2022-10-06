package kitchenpos.menus.tobe.domain.listener;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductPriceChangedListener {

    private final MenuRepository menuRepository;

    public ProductPriceChangedListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @EventListener(ProductPriceChangedEvent.class)
    public void handleProductPriceChange(ProductPriceChangedEvent evt) {
        UUID productId = evt.getProductId();
        Long changedPrice = evt.getChangedPrice();

        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(it -> it.updateProduct(productId, changedPrice));
    }
}
