package kitchenpos.common.tobe.domain;

import kitchenpos.common.tobe.exception.WrongDisplayedNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.common.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY;
import static kitchenpos.common.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(final String name, final Profanities profanities) {
        validateName(name, profanities);
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

    public void validateName(final String name, final Profanities profanities) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new WrongDisplayedNameException(DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY);
        }
        if (profanities.containsProfanity(name)) {
            throw new WrongDisplayedNameException(DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY);
        }
    }
}
