package kitchenpos.products.ui.dto;

import java.math.BigDecimal;

public class ChangePriceRequest {

    private BigDecimal price;

    public ChangePriceRequest() {
    }

    public ChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
