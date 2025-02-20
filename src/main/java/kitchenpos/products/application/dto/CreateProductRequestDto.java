package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public class CreateProductRequestDto {

    private String name;
    private BigDecimal price;

    public CreateProductRequestDto() {

    }

    public CreateProductRequestDto(
        String name,
        BigDecimal price
    ) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
