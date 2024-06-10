package kitchenpos.products.ui.response;

import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {

    private final UUID id;

    private final String name;

    private final BigDecimal price;

    public ProductDto(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name.getName();
        this.price = price.getPrice();
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
