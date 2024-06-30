package kitchenpos.products.tobe.fixtures;

import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import kitchenpos.products.tobe.Product;
import kitchenpos.products.tobe.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductStep {

    private ProductRepository productRepository;

    public ProductStep(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product 상품_생성() {
        return 상품_생성(UUID.randomUUID(), new Name("상품"), new Money(BigDecimal.valueOf(1000L)));
    }

    public Product 상품_생성(UUID productId) {
        return 상품_생성(productId, new Name("상품"), new Money(BigDecimal.valueOf(1000L)));
    }

    public Product 상품_생성(UUID productId, Name name) {
        return 상품_생성(productId, name, new Money(BigDecimal.valueOf(1000L)));
    }

    public Product 상품_생성(Money price) {
        return 상품_생성(UUID.randomUUID(), new Name("상품"), price);
    }

    public Product 상품_생성(UUID productId, Name name, Money price) {
        final var product = new Product(productId, name, price);
        return productRepository.save(product);
    }
}
