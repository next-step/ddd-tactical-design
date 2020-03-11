package kitchenpos.products.tobe.web.dto;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.entity.Price;
import kitchenpos.products.tobe.domain.entity.Product;

public class ProductSaveRequestDto {

    private String name;

    private BigDecimal price;

    public ProductSaveRequestDto() {
    }

    public ProductSaveRequestDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity() {
        return new Product(name, Price.valueOf(price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
