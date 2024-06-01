package kitchenpos.products.tobe.domain.application;

import java.math.BigDecimal;
import java.util.Objects;
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
    public Product execute(ProductCreateDto request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }
}
