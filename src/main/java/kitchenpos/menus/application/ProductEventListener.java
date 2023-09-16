package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceChangeEvent;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ProductEventListener {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductEventListener(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void changeMenuPrice(ProductPriceChangeEvent event) {
        final Product product = productRepository.findById(event.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            menu.changeMenuProductPrice(product.getId(), product.getPrice().getValue());
        }
        menuRepository.saveAll(menus);
    }
}
