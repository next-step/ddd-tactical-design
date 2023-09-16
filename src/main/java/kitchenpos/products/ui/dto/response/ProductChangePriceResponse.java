package kitchenpos.products.ui.dto.response;

import kitchenpos.products.domain.Product;

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

    public static ProductChangePriceResponse of(Product product) {
        return new ProductChangePriceResponse(product.getId(), product.getName().getValue(), product.getPrice().getValue());
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
