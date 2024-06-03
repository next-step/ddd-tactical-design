package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;
import kitchenpos.common.purgomalum.PurgomalumClient;

public class ProductName {
    private final String value;

    private ProductName(String value) {
        this.value = value;
    }

    public static ProductName of(String value, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("Product name cannot be null");
        } else if (purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException("Product name contains profanity");
        }
        return new ProductName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
