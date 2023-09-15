package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;

import java.math.BigDecimal;

public class ProductResponse {
    private ProductId id;

    private String name;

    private BigDecimal price;

    public ProductResponse(ProductId id, String name, BigDecimal price) {
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
                product.getId(),
                product.getNameValue(),
                product.getPriceValue());
    }
}
