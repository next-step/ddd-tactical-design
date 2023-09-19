package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuDisplayedName {
    @Column(name = "displayed_name", nullable = false)
    private String value;

    protected MenuDisplayedName() {
    }

    private MenuDisplayedName(final String value) {
        this.value = value;
    }

    public static MenuDisplayedName from(final String value, final MenuDisplayedNamePolicy menuDisplayedNamePolicy) {
        MenuDisplayedName menuDisplayedName = new MenuDisplayedName(value);
        menuDisplayedNamePolicy.validateDisplayName(menuDisplayedName);
        return menuDisplayedName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDisplayedName that = (MenuDisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
