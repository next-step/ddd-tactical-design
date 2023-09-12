package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, PurgomalumChecker purgomalumChecker) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품의 이름은 비어 있을 수 없습니다.");
        }

        if (purgomalumChecker.containsProfanity(name)) {
            throw new IllegalArgumentException("이름에는 비속어가 포함될 수 없습니다. name: " + name);
        }

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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
