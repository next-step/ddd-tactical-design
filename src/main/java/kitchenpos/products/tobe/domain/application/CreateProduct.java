package kitchenpos.products.tobe.domain.application;

import java.util.UUID;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
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
    public Product execute(ProductCreateDto request) {
        final ProductPrice price = ProductPrice.of(request.getPrice());
        final ProductName name = ProductName.of(request.getName(), purgomalumClient);
        final Product product = new Product(
            UUID.randomUUID(),
            name,
            price
        );
        return productRepository.save(product);
    }
}
