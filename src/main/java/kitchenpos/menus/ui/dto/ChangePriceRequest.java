package kitchenpos.menus.ui.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ChangePriceRequest {
    public BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePriceRequest that = (ChangePriceRequest) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
