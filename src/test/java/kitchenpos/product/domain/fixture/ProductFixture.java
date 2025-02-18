package kitchenpos.product.domain.fixture;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductVo;

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

    public ProductVo.Create create() {
        return new ProductVo.Create(상품명, 상품가격);
    }

    public ProductVo.Update update() {
        return new ProductVo.Update(id, 상품가격);
    }
}

