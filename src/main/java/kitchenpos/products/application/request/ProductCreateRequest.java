package kitchenpos.products.application.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, Long price) {
        this.name = name;
        this.price = price == null ? null : BigDecimal.valueOf(price);
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
