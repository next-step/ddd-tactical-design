package kitchenpos.menus.tobe.domain;

import kitchenpos.common.DomainService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@DomainService
public class MenuProductFactory {

    private final ProductRepository productRepository;

    public MenuProductFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MenuProduct create(final UUID productId, final long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        return new MenuProduct(
                productId,
                product.getPrice().getValue(),
                quantity
        );
    }
}
