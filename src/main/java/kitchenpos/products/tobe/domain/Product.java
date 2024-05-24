package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;

public class Product {
    final Name name;
    final Price price;

    public Product(Name name, Price price) {
        this.name = name;
        this.price = price;
    }
}
