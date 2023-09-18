package kitchenpos.menus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class MenuChangePriceRequest {
    @JsonProperty("price")
    private final BigDecimal price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public MenuChangePriceRequest(@JsonProperty("price") BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }
}
