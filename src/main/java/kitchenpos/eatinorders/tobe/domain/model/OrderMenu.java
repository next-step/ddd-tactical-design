package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderMenu {

    private final UUID id;

    private final Price price;

    private final boolean displayed;

    public OrderMenu(final UUID id, final Price price, final boolean displayed) {
        this.id = id;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
