package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateResponse {
    private UUID id;
    private String name;
    private BigDecimal price;

    public ProductCreateResponse() {
    }

    private ProductCreateResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductCreateResponse of(UUID id, String name, BigDecimal price) {
        return new ProductCreateResponse(id, name, price);
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
