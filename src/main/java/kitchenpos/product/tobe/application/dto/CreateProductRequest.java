package kitchenpos.product.tobe.application.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class CreateProductRequest {

    @NotBlank
    private final String name;

    @PositiveOrZero
    private final BigDecimal price;

    public CreateProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
