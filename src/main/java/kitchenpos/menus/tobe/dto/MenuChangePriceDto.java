package kitchenpos.menus.tobe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;

public class MenuChangePriceDto {
    private BigDecimal price;

    @JsonCreator
    public MenuChangePriceDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
