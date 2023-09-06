package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private UUID id;

    private String name;

    private BigDecimal price;

    public ProductResponse(UUID id, String name, BigDecimal price) {
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

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId()
                , product.getNameValue()
                , product.getPriceValue());
    }
}
