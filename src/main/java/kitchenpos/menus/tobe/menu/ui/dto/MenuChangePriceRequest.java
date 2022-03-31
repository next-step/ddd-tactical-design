package kitchenpos.menus.tobe.menu.ui.dto;

import kitchenpos.menus.tobe.menu.domain.product.Price;

import java.math.BigDecimal;

public class MenuChangePriceRequest {
    private final BigDecimal price;

    public MenuChangePriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public Price price() {
        return new Price(price);
    }
}
