package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.exception.ContainsProfanityException;
import kitchenpos.products.tobe.exception.DisplayedNameEmptyException;

import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {}

    protected DisplayedName(String name, ProfanityValidator profanityValidator) {
        validate(name, profanityValidator);
        this.name = name;
    }

    private void validate(String name, ProfanityValidator profanityValidator) {
        if (this.isNull(name)) {
            throw new DisplayedNameEmptyException();
        }
        if (!profanityValidator.validate(name)) {
            throw new ContainsProfanityException();
        }
    }

    private boolean isNull(String name) {
        return Objects.isNull(name);
    }

    public static DisplayedName of(String name, ProfanityValidator profanityValidator) {
        return new DisplayedName(name, profanityValidator);
    }

    public String value() {
        return this.name;
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
