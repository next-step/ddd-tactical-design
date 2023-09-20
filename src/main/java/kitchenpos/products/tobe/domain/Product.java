package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class Product {
    private UUID id;
    private DisplayedName name;
    private Price price;

    public Product(UUID id, DisplayedName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
