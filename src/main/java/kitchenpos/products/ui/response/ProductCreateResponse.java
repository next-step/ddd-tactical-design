package kitchenpos.products.ui.response;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateResponse {

    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductCreateResponse(final UUID id, final String name, final BigDecimal price) {
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
