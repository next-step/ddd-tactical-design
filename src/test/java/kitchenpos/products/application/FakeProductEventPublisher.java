package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductEventPublisher;
import kitchenpos.products.tobe.domain.ProductPriceChangeEvent;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.List;

public class FakeProductEventPublisher extends ProductEventPublisher {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public FakeProductEventPublisher(ProductRepository productRepository, MenuRepository menuRepository) {
        super(null);
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void changePrice(ProductPriceChangeEvent event) {
        Product product = productRepository.findById(event.getProductId())
                .orElseThrow();
        List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (Menu menu : menus) {
            menu.changeMenuProductPrice(product);
        }
    }
}
