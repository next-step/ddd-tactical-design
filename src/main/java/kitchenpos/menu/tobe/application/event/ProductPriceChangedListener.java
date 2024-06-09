package kitchenpos.menu.tobe.application.event;

import kitchenpos.common.event.publisher.ProductPriceChangedEvent;
import kitchenpos.menu.tobe.domain.menu.Menu;
import kitchenpos.menu.tobe.domain.menu.MenuRepository;
import kitchenpos.menu.tobe.domain.menu.ProductPrice;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductPriceChangedListener {
    private final MenuRepository menuRepository;

    public ProductPriceChangedListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @EventListener
    @Transactional
    public void handle(ProductPriceChangedEvent event) {
        List<Menu> menus = menuRepository.findAllByProductId(event.productId());
        for (Menu menu : menus) {
            ProductPrice productPrice = new ProductPrice(event.price());
            menu.changeMenuProductPrice(event.productId(), productPrice);
        }
    }
}
