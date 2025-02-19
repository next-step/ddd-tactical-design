package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateProductRequestDto {

    private UUID id;
    private String name;
    private BigDecimal price;

    public CreateProductRequestDto() {

    }

    public CreateProductRequestDto(
        UUID id,
        String name,
        BigDecimal price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
