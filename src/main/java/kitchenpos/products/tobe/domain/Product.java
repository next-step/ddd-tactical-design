package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;

public class Product {
    final Name name;
    final Price price;

    public Product(Name name, Price price) {
        if (price.getValue() <= 0) {
            throw new IllegalArgumentException("Price should be over zero, not negative");
        }
        this.name = name;
        this.price = price;
    }
}
