package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ProductCreateRequestDto {
    private String name;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
