package kitchenpos.products.ui.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRestResponse {
    private final UUID id;

    private final String name;

    private final BigDecimal price;

    public ProductRestResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
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
