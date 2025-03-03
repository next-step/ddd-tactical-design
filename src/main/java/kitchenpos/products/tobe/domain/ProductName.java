package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(final String name, final PurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명에 욕설이 포함되어 있습니다.");
        }
        this.name = name;
    }

    public String value() {
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
        return Objects.hashCode(name);
    }
}
