package kitchenpos.products.dto;

import java.math.BigDecimal;

public class CreateReqeust {

    private String name;
    private BigDecimal price;

    public CreateReqeust() {
    }

    public CreateReqeust(String name, BigDecimal price) {
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
