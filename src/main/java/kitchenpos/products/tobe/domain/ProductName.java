package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    private String name;

    protected ProductName() {
    }

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        validate(name, purgomalumClient);
        this.name = name;
    }

    private void validate(String name, PurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수항목입니다. 상품이름을 확인해주세요.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품의 이름에는 비속어가 포함될 수 없습니다. 상품이름을 확인해주세요.");
        }
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
