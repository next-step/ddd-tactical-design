package kitchenpos.products.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class ProductChangePriceRequest {
    @JsonProperty("price")
    private final BigDecimal price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductChangePriceRequest(@JsonProperty("price") BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }
}
