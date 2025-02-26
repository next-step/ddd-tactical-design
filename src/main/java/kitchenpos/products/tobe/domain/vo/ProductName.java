package kitchenpos.products.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;

import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(final String name) {
        this.name = checkProductName(name);
    }

    private String checkProductName(final String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidProductException("상품의 이름이 존재해야 한다.");
        }
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
