package kitchenpos.menus.menu.tobe.infra;

import kitchenpos.menus.menu.tobe.domain.ProductContextClient;
import kitchenpos.menus.menu.tobe.domain.vo.ProductSpecification;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductContextRepositoryClient implements ProductContextClient {

    private final ProductRepository productRepository;

    public ProductContextRepositoryClient(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductSpecification findProductById(final UUID productId) {
        final Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        return new ProductSpecification(product.id(), product.price().toLong());
    }
}
