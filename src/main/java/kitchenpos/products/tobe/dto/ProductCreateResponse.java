package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateResponse {
    private UUID id;
    private String name;
    private BigDecimal price;

    private ProductCreateResponse() {
    }

    private ProductCreateResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductCreateResponse of(final Product product) {
        return new ProductCreateResponse(
                product.getId(),
                product.getName().getValue(),
                product.getPrice().getValue()
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
