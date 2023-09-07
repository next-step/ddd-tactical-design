package kitchenpos.products.tobe.ui;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductResponse {

    private final String id;

    private final String name;

    @NotNull
    private final BigDecimal price;

    public ProductResponse(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
