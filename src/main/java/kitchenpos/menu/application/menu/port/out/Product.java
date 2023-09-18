package kitchenpos.menu.application.menu.port.out;

import java.util.UUID;
import kitchenpos.menu.domain.menu.ProductPrice;

public final class Product {

    private final UUID id;
    private final ProductPrice price;

    public Product(final UUID id, final int price) {
        this.id = id;
        this.price = ProductPrice.create(price);
    }

    public UUID getId() {
        return id;
    }

    public ProductPrice getPrice() {
        return price;
    }
}