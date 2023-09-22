package kitchenpos.products.application.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductNamePolicy;

import java.math.BigDecimal;

public class ProductCreateRequest {
    private final String name;
    private final BigDecimal price;

    public ProductCreateRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct(ProductNamePolicy productNamePolicy) {
        return Product.from(name, price, productNamePolicy);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
