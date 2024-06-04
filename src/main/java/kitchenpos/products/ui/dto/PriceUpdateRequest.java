package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.tobe.Price;

public class PriceUpdateRequest {

    private BigDecimal price;

    public PriceUpdateRequest(BigDecimal price) {
        this.price = price;
    }

    public Price to(){
        return new Price(price);
    }
}
