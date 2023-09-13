package kitchenpos.apply.products.tobe.domain;

import kitchenpos.support.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductName extends ValueObject {
    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() { }

    public ProductName(String name) {
        this.value = name;
    }

    public String value() {
        return value;
    }
}