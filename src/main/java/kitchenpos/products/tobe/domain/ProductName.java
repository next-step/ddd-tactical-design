package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class ProductName {
    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {
    }

    public static ProductName create(String value, PurgomalumClient purgomalumClient) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
        }

        boolean isContainingProfanity = purgomalumClient.containsProfanity(value);
        if (isContainingProfanity) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }

        ProductName productName = new ProductName();
        productName.value = value;
        return productName;
    }

    protected String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
