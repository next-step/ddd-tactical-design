package kitchenpos.products.ui.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class ProductRestRequest {
    @NotBlank
    final private String name;
    @NotEmpty
    @Min(0)
    final private BigDecimal price;

    public ProductRestRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductRestRequest(String name, long price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
