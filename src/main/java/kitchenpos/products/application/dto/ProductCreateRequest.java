package kitchenpos.products.application.dto;

import kitchenpos.products.tobe.domain.ProductDisplayedNamePolicy;
import kitchenpos.products.tobe.domain.ProductDisplayedName;
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

    public Product toProduct(ProductDisplayedNamePolicy productDisplayedNamePolicy) {
        return new Product(ProductDisplayedName.from(name, productDisplayedNamePolicy), ProductPrice.from(price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
