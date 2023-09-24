package kitchenpos.products.application.dto;

import kitchenpos.products.tobe.domain.ProductId;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private final UUID id;

    private final String name;

    private final BigDecimal price;

    public ProductResponse(ProductId id, String name, BigDecimal price) {
        this.id = id.getValue();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

}
