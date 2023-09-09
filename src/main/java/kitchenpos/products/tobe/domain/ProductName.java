package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductException;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class ProductName {

    private String name;

    protected ProductName() {
    }

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        if (isNullOrEmpty(name)) {
            throw new ProductException(ProductErrorCode.NAME_IS_NOT_EMPTY_OR_NULL);
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new ProductException(ProductErrorCode.NAME_CONTAINS_PROFANITY);
        }
        this.name = name;
    }

    private boolean isNullOrEmpty(String name) {
        return name == null || name.isEmpty();
    }

    public String getValue() {
        return name;
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
