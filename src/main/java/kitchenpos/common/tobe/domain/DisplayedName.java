package kitchenpos.common.tobe.domain;

import static kitchenpos.products.tobe.exception.WrongDisplayedNameExeption.DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY;
import static kitchenpos.products.tobe.exception.WrongDisplayedNameExeption.DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.common.infra.Profanities;
import kitchenpos.products.tobe.exception.WrongDisplayedNameExeption;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void validateName(final String name, final Profanities profanities) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new WrongDisplayedNameExeption(DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY);
        }
        if (profanities.contains(name)) {
            throw new WrongDisplayedNameExeption(DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY);
        }
    }

}
