package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.DisplayedNameException;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.tobe.domain.policy.ProfanityPolicy;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private String name;

    protected DisplayedName() {

    }

    public DisplayedName(String name, ProfanityPolicy profanityPolicy) {
        validate(name, profanityPolicy);
        this.name = name;
    }

    private void validate(String text, ProfanityPolicy profanityPolicy) {
        if (isNullAndEmpty(text)) {
            throw new DisplayedNameException(ProductErrorCode.NAME_IS_NULL_OR_EMPTY);
        }

        if (profanityPolicy.containsProfanity(text)) {
            throw new DisplayedNameException(ProductErrorCode.NAME_HAS_PROFANITY);
        }
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
