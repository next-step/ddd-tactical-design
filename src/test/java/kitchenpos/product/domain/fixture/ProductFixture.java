package kitchenpos.product.domain.fixture;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;

public record ProductFixture(UUID id, String 상품명, BigDecimal 상품가격) {

    public static final String DEFAULT_PRODUCT_NAME = "후라이드 치킨";
    public static final BigDecimal DEFAULT_PRODUCT_PRICE = BigDecimal.valueOf(20_000);

    public static ProductFixture init() {
        return new ProductFixture(UUID.randomUUID(), DEFAULT_PRODUCT_NAME, DEFAULT_PRODUCT_PRICE);
    }

    public static ProductFixture test(String 상품명, BigDecimal 상품가격) {
        return new ProductFixture(
            UUID.randomUUID(),
            Objects.requireNonNullElse(상품명, DEFAULT_PRODUCT_NAME),
            Objects.requireNonNullElse(상품가격, DEFAULT_PRODUCT_PRICE)
        );
    }

    public Product toEntity() {
        return new Product(id, new ProductName(상품명), ProductPrice.of(상품가격));
    }

    public ProductRequest.Create create() {
        return new ProductRequest.Create(상품명, 상품가격);
    }

    public ProductRequest.UpdatePrice update() {
        return new ProductRequest.UpdatePrice(id, 상품가격);
    }
}

