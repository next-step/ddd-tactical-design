package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.policy.ProfanityPolicy;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class ProductRequest {
    @NotBlank
    private String name;
    @NotEmpty
    @Min(0)
    private BigDecimal price;

    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductRequest(String name, long price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity(ProfanityPolicy profanityPolicy) {
        return Product.of(this.name, this.price, profanityPolicy);
    }
}
