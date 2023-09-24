package kitchenpos.eatinorders.tobe.domain;


import java.math.BigDecimal;
import java.util.UUID;

public class OrderMenu {
    private final UUID id;
    private final boolean displayed;
    private final BigDecimal menuPrice;

    public OrderMenu(final UUID id, final boolean displayed, final BigDecimal menuPrice) {
        this.id = id;
        this.displayed = displayed;
        this.menuPrice = menuPrice;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public BigDecimal getMenuPrice() {
        return menuPrice;
    }
}
