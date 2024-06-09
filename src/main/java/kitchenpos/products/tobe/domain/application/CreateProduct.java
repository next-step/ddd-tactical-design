package kitchenpos.products.tobe.domain.application;

import java.util.UUID;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import org.springframework.stereotype.Component;

@FunctionalInterface
public interface CreateProduct {
    Product execute(ProductCreateDto productCreateDto);
}


@Component
class DefaultCreateProduct implements CreateProduct {
    private final PurgomalumClient purgomalumClient;
    private final ProductRepository productRepository;

    public DefaultCreateProduct(PurgomalumClient purgomalumClient, ProductRepository productRepository) {
        this.purgomalumClient = purgomalumClient;
        this.productRepository = productRepository;
    }

    @Override
    public final Product execute(ProductCreateDto request) {
        final Product product = Product.createProduct(UUID.randomUUID(), request.getName(), request.getPrice(), purgomalumClient);
        return productRepository.save(product);
    }
}
