package kitchenpos.products.ui.dto;

import java.math.BigDecimal;

public class ChangePriceRequest {

    private BigDecimal price;

    public ChangePriceRequest() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
