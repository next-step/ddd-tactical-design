package kitchenpos.products.dto;

import kitchenpos.support.dto.DTO;

import java.math.BigDecimal;

public class ProductRegisterRequest extends DTO {
    private String name;
    private BigDecimal price;

    public ProductRegisterRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}


