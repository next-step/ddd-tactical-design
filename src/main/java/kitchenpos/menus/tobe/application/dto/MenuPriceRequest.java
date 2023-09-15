package kitchenpos.menus.tobe.application.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPriceRequest {
    private BigDecimal price;

    public MenuPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
