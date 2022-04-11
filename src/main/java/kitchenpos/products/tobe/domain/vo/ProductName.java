package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;

import kitchenpos.common.ValueObject;
import kitchenpos.products.application.port.out.PurgomalumClient;

@ValueObject
public final class ProductName {

    private String name;

    private ProductName() {
    }

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        validation(name, purgomalumClient);
        this.name = name;
    }

    private void validation(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("상품의 이름은 비어 있을 수 없습니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품의 이름은 비속어를 포함할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
