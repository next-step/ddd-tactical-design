package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.exception.ProductNameNullPointerException;
import kitchenpos.products.exception.ProductNameProfanityException;

import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    protected ProductName(String name) {
        this.name = name;
        nullCheck(name);
    }

    protected ProductName(String name, ProfanityChecker profanityChecker) {
        this(name);
        this.containsProfanity(name, profanityChecker);
    }

    public static ProductName from(String name) {
        return new ProductName(name);
    }

    public static ProductName from(String name, ProfanityChecker profanityChecker) {
        return new ProductName(name, profanityChecker);
    }

    public String nameValue() {
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

    private void nullCheck(String name) {
        if (Objects.isNull(name)) {
            throw new ProductNameNullPointerException();
        }
    }

    private void containsProfanity(String name, ProfanityChecker profanityChecker) {
        if (profanityChecker.containsProfanity(name)) {
            throw new ProductNameProfanityException(name);
        }
    }
}
