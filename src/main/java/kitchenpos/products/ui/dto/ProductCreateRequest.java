package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.tobe.Product;

public class ProductCreateRequest {

    private final String name;

    private final BigDecimal price;

    public ProductCreateRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product to() {
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }
}
