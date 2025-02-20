package kitchenpos.product.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.product.tobe.Profanities;

import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    public ProductName(String name, Profanities profanities) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수값입니다.");
        }

        if (profanities.contains(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
        this.name = name;
    }

    public ProductName() {}

    ProductName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductName that = (ProductName) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
