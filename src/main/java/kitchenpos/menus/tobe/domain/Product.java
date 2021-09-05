package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    public final UUID id;

    public final Price price;

    public Product(final UUID id, final Price price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.value();
    }
}
