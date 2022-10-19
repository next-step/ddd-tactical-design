package kitchenpos.product.tobe.domain.entity;

import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;

public class Product {

    public final UUID id;

    public final Name name;

    private Price price;

    public Product(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Price price() {
        return this.price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }
}
