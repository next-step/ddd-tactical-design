package kitchenpos.products.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    private ProductName(final String name) {
        this.name = name;
    }

    public static ProductName of(final String name, ProductPurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        return new ProductName(name);
    }

    private static void validateName(final String name, ProductPurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품명이 비어 있습니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되었습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
