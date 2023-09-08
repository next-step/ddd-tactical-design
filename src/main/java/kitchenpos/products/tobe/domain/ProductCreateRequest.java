package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class ProductCreateRequest {
    private String displayedName;
    private BigDecimal price;

    public ProductCreateRequest() {
    }

    public void setName(String name) {
        this.displayedName = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
