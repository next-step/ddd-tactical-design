package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.Profanities;
import kitchenpos.products.tobe.exception.WrongDisplayedNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.products.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY;
import static kitchenpos.products.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayedName)) return false;
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public static void validateName(Profanities profanities, String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new WrongDisplayedNameException(DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY);
        }
        if (profanities.containsProfanity(name)) {
            throw new WrongDisplayedNameException(DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY);
        }
    }
}
