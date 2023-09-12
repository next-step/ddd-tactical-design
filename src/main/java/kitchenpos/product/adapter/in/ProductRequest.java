package kitchenpos.product.adapter.in;

import java.math.BigDecimal;
import java.util.UUID;

class ProductRequest {
    private UUID id;

    private String name;

    private BigDecimal price;

    ProductRequest(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
