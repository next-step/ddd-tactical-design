package kitchenpos.products.tobe.domain.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductBuilder;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private BigDecimal price;

    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return ProductBuilder.aProduct()
                .withName(this.name)
                .withPrice(this.price)
                .build();
    }
}
