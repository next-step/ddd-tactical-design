package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.ChangeProductPriceEvent;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ChangeProductPriceEventHandler {

    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    public ChangeProductPriceEventHandler(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @EventListener
    @Transactional
    public void handle(ChangeProductPriceEvent event) {

        Product product = productRepository.findById(event.getProductId())
                .orElseThrow(NoSuchElementException::new);

        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            menu.changeProductPrice(event.getProductId(), product.getPrice());
            menu.overMenuProductsTotalPriceThenHide();
        }
    }

}
