package kitchenpos.products.tobe.ui.dto;

import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.Profanities;

import java.math.BigDecimal;

public class ProductCreateRequest {
    private final String name;
    private final BigDecimal price;

    public ProductCreateRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity(Profanities profanities) {
        return new Product(new Name(name, profanities), new Price(price));
    }
}
