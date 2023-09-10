package kitchenpos.product.adapter.in;

import java.math.BigDecimal;

class CreateProductRequest {
    private String name;

    private BigDecimal price;

    CreateProductRequest(String name, BigDecimal price) {
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
