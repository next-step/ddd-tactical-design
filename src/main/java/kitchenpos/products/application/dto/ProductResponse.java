package kitchenpos.products.application.dto;

import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private final UUID productId;

    private final String name;

    private final BigDecimal price;

    public ProductResponse(final Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
