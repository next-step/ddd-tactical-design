package kitchenpos.products.ui.response;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;

public class ProductResponse {

    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getNameValue(),
            product.getPriceValue()
        );
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
