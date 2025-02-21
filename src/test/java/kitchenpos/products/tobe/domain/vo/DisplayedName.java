package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.exception.DisplayedNameContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.InvalidDisplayedNameException;

import java.util.Objects;

public class DisplayedName {
    private final String productName;

    public DisplayedName(final String productName) {
        if (Objects.isNull(productName) || productName.isBlank()) {
            throw new InvalidDisplayedNameException();
        }
        this.productName = productName;
    }

    public DisplayedName(final String displayedName, final ProfanityName profanityName) {
        this(displayedName);
        if (profanityName.contains(productName)) {
            throw new DisplayedNameContainsProfanityException();
        }
    }

    public String getDisplayedName() {
        return productName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}


