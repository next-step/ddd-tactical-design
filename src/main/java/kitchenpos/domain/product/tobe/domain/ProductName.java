package kitchenpos.domain.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name) {
        this(name, (text) -> false);
    }

    public ProductName(String name, BlackWordClient blackWordClient) {
        validateName(name);
        boolean containsProfanity = blackWordClient.containsProfanity(name);
        if (containsProfanity) {
            throw new IllegalArgumentException("name에 비속어가 포함될 수 없습니다.");
        }
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("name은 Null 일 수 없습니다.");
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
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
