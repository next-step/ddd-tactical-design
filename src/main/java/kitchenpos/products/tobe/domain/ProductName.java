package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.exception.message.ProductErrorCode;
import kitchenpos.products.tobe.exception.message.ProductNameException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {

    }

    public ProductName(String name, PurgomalumClient profanityClient) {
        if (isNullAndEmpty(name)) {
            throw new ProductNameException(ProductErrorCode.NAME_IS_NULL_OR_EMPTY);
        }
        if (profanityClient.containsProfanity(name)) {
            throw new ProductNameException(ProductErrorCode.NAME_HAS_PROFANITY);
        }
        this.name = name;
    }


    private boolean isNullAndEmpty(String name) {
        return name == null || name.isBlank();
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
