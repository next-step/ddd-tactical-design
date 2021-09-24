package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

public class MenuProduct {

    private Price price;
    private Quantity quantity;

    public MenuProduct(Price price, Quantity quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return price.getPrice().multiply(quantity.getQuantity());
    }

}
