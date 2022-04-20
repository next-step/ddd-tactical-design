package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private final BigDecimal price;

    protected MenuPrice() {
        this.price = null;
    }

    public MenuPrice(BigDecimal price) {
        this.price = price;
    }
}
