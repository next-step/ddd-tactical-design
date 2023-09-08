package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class MenuPrice {
    private BigDecimal value;

    protected MenuPrice() { }

    public MenuPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new IllegalArgumentException("price는 0보다 높아야 한다");
        }
        this.value = price;
    }

    public BigDecimal value() {
        return value;
    }
}
