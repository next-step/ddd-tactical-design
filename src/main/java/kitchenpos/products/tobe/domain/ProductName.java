package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ProductName {
    private String value;

    protected ProductName() { }

    public ProductName(String name) {
        this.value = name;
    }

    public String value() {
        return value;
    }
}