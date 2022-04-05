package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ProductName {
    private final String name;

    protected ProductName() {
        this.name = null;
    }

    public ProductName(String name) {
        this.name = name;
    }
}
