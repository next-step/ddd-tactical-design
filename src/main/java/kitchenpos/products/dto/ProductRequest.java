package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductRequest {
    private UUID id;

    @NotBlank
    private String name;

    @NotEmpty
    @Min(0)
    private BigDecimal price;

    public ProductRequest(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static ProductRequest fromEntity(Product product){
        return new ProductRequest(
                product.getId()
                , product.getNameValue()
                , product.getPriceValue());
    }
}
