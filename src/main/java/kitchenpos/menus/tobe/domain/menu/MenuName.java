package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    private MenuName(final String value) {
        this.value = value;
    }

    public static MenuName from(final String value, final MenuNamePolicy menuNamePolicy) {
        MenuName menuName = new MenuName(value);
        menuNamePolicy.validateName(menuName);
        return menuName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName that = (MenuName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
