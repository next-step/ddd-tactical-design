package kitchenpos.apply.products.tobe.domain;

import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class ProductName extends ValueObject {
    private String value;

    protected ProductName() { }

    public ProductName(String name) {
        this.value = name;
    }

    public String value() {
        return value;
    }
}