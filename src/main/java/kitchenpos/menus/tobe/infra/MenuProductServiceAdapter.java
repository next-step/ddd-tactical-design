package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuProductServiceAdapter implements MenuProductService {

    private final ProductRepository productRepository;

    public MenuProductServiceAdapter(
        ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public MenuProduct getMenuProduct(long seq, long quantity) {
        return this.productRepository.findById(seq)
            .map(product -> map(product, quantity))
            .orElseThrow(() -> new IllegalArgumentException("not exist product"));
    }

    private MenuProduct map(Product product, long quantity) {
        return new MenuProduct(product.getId(), quantity, product.getPrice());
    }

}
