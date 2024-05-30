package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;

public class ProductResponse {
    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductResponse(final UUID id,
                           final String name,
                           final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
