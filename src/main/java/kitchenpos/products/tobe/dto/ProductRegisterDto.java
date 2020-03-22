package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductRegisterDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;

    public ProductRegisterDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
