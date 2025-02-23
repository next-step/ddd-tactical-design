package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.exception.InvalidProductNameException;

import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {}

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        validate(name, purgomalumClient);
        this.name = name;
    }

    private void validate(String name, PurgomalumClient purgomalumClient) {
        if (name == null || purgomalumClient.containsProfanity(name)) {
            throw new InvalidProductNameException("상품명에 비속어는 입력할 수 없습니다");
        }
    }

    public String getValue() {
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
        return Objects.hashCode(name);
    }
}
