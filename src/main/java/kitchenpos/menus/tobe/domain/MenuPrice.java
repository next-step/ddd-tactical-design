package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    private BigDecimal price;

    protected MenuPrice() {
    }

    private MenuPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public static MenuPrice of(BigDecimal price) {
        return new MenuPrice(price);
    }

    public void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
    }

    public BigDecimal price() {
        return price;
    }
}
