package kitchenpos.menus.tobe.domain.menugroup;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.exception.InvalidMenuGroupNameException;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    protected MenuGroupName(String name) {
        validate(name);
        this.name = name;
    }

    public static MenuGroupName from(String name) {
        return new MenuGroupName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String nameValue() {
        return name;
    }

    private static void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new InvalidMenuGroupNameException();
        }
    }
}
