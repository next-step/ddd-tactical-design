package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private static final String PRODUCT_NAME_MUST_NOT_BE_EMPTY = "상품 이름은 빈 값이 아니어야 합니다. 입력 값 : %s";
    private static final String PRODUCT_NAME_MUST_NOT_BE_PROFANITY = "상품 이름에는 비속어가 포함되지 않아야 합니다. 입력 값 : %s";

    private final String name;

    protected ProductName() {
        this.name = null;
    }

    protected ProductName(PurgomalumClient purgomalumClient, String name) {
        validate(purgomalumClient, name);
        this.name = name;
    }

    private void validate(PurgomalumClient purgomalumClient, String name) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException(String.format(PRODUCT_NAME_MUST_NOT_BE_EMPTY, name));
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(String.format(PRODUCT_NAME_MUST_NOT_BE_PROFANITY, name));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
