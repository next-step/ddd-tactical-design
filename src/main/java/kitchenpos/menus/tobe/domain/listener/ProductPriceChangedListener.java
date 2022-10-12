package kitchenpos.menus.tobe.domain.listener;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.events.ProductPriceChangedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;

@Component
public class ProductPriceChangedListener {

    private final MenuRepository menuRepository;

    public ProductPriceChangedListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleProductPriceChange(ProductPriceChangedEvent evt) {
        UUID productId = evt.getProductId();
        Long changedPrice = evt.getChangedPrice();

        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(it -> it.updateProduct(productId, changedPrice));
    }
}
