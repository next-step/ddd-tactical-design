package kitchenpos.menus.menugroup.tobe.domain;

import kitchenpos.menus.menugroup.tobe.domain.exception.InvalidMenuGroupNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() {
    }

    private MenuGroupName(final String value) {
        this.value = value;
    }

    public static MenuGroupName valueOf(final String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new InvalidMenuGroupNameException();
        }
        return new MenuGroupName(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
