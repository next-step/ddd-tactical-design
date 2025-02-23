package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.exception.DisplayedNameContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.InvalidDisplayedNameException;

import java.util.Objects;

public class DisplayedName {
    private final String value;

    public DisplayedName(final String value, final Profanities profanities) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidDisplayedNameException();
        }
        if (profanities.contains(value)) {
            throw new DisplayedNameContainsProfanityException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}


