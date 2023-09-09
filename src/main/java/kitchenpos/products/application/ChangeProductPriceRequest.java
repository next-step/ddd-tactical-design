package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ChangeProductPriceRequest {
    private UUID id;
    private BigDecimal price;

    private ChangeProductPriceRequest(UUID id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public ChangeProductPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public static ChangeProductPriceRequest of(UUID productId, BigDecimal price) {
        return new ChangeProductPriceRequest(productId, price);
    }

    public static ChangeProductPriceRequest of(BigDecimal price) {
        return new ChangeProductPriceRequest(price);
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
