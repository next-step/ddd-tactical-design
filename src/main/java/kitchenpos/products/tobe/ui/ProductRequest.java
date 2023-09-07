package kitchenpos.products.tobe.ui;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductRequest {
    
    private final String name;

    @NotNull
    private final BigDecimal price;

    public ProductRequest(String name, BigDecimal price) {
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
