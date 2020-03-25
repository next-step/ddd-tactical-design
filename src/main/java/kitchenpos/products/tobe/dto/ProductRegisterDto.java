package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.infra.ProductEntity;

import java.math.BigDecimal;

public class ProductRegisterDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;

    public ProductRegisterDto(ProductEntity productEntity){
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
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
