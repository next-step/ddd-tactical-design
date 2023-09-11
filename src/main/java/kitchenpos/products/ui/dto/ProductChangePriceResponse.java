package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductChangePriceResponse {
    private UUID id;
    private String name;
    private BigDecimal price;

    private ProductChangePriceResponse() {
    }

    private ProductChangePriceResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductChangePriceResponse of(UUID id, String name, BigDecimal price) {
        return new ProductChangePriceResponse(id, name, price);
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
