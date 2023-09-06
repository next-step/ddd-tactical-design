package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductNameException;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private String name;

    protected DisplayedName() {

    }

    public DisplayedName(String name, PurgomalumClient profanityClient) {
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

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
