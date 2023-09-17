package kitchenpos.menus.domain.tobe.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.products.domain.tobe.domain.ProductPriceChangeEvent;
import kitchenpos.products.domain.tobe.domain.ProductPriceChangeRequest;
import kitchenpos.products.domain.tobe.domain.ToBeProductRepository;

@Component
public class MenuDisplayListener {
    private final ToBeMenuRepository menuRepository;
    private final ToBeProductRepository productRepository;

    public MenuDisplayListener(ToBeMenuRepository menuRepository, ToBeProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeDisplay(ProductPriceChangeEvent event) {
        ProductPriceChangeRequest request = (ProductPriceChangeRequest)event.getSource();
        productRepository.findById(request.getProductId())
            .ifPresent(toBeProduct -> {
                List<ToBeMenu> menus = menuRepository.findAllByProductId(request.getProductId());
                for (ToBeMenu it : menus) {
                    it.changeProductPrice(request.getProductId(), toBeProduct.getPrice().getValue());
                }
            });
    }
}
