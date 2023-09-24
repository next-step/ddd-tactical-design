package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    @Column(name = "name", nullable = false)
    private String value;

    protected Name() {
    }

    private Name(final String value) {
        this.value = value;
    }

    public static Name from(final String value, final MenuNamePolicy menuNamePolicy) {
        Name name = new Name(value);
        menuNamePolicy.validateName(name);
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
