package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {}

    protected ProductName(final String name,
                          final PurgomalumClient purgomalumClient) {
        this.setName(name, purgomalumClient);
    }

    private void setName(final String name,
                         final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력하지 않았거나, 공백을 입력할 수 없습니다.");
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 담긴 이름은 사용할 수 없습니다.");
        }

        this.name = name;
    }

    protected String getName() {
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
