package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;

    public ProductDto (Product product){
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
