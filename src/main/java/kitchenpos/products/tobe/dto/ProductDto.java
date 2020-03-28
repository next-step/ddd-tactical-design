package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.infra.ProductEntity;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;

    public ProductDto (ProductEntity productEntity){
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
