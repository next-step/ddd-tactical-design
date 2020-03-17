package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.tobe.domain.Price;

import java.math.BigDecimal;

public class ProductPriceResponse {
    private Long id;

    private Price price;

    public Long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public ProductPriceResponse(Long id, BigDecimal price) {
        this.id = id;
        this.price = new Price(price);
    }
}
