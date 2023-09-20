package kitchenpos.products.application.dto;

import kitchenpos.products.tobe.domain.ProductNamePolicy;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class ProductCreateRequest {
    private final String name;
    private final BigDecimal price;

    public ProductCreateRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct(ProductNamePolicy productNamePolicy) {
        return new Product(ProductName.from(name, productNamePolicy), ProductPrice.from(price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
