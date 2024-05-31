package kitchenpos.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.product.tobe.domain.validate.ProfanityValidator;

import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, ProfanityValidator profanityValidator) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품명이 비어있습니다.");
        }
        profanityValidator.validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
