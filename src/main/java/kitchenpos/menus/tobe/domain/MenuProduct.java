package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

import java.math.BigDecimal;

public class MenuProduct {

    private final Price price;
    private final Quantity quantity;

    public MenuProduct(Price price, Quantity quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return price.getPrice().multiply(quantity.getQuantity());
    }

}
