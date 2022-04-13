package kitchenpos.products.tobe.domain.model;

import kitchenpos.global.domain.vo.DisplayedName;
import kitchenpos.global.domain.vo.Price;

import java.util.UUID;


public final class Product {

    private final UUID id;
    private final DisplayedName name;
    private Price price;

    public Product(DisplayedName name, Price price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public void changePrice(Price price) {
        if (this.price.isNotSame(price)) {
            this.price = price;
        }
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
