package kitchenpos.menu.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menu.tobe.domain.service.MenuNamePolicy;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    public MenuName(String value) {
        this.value = value;
    }

    public static MenuName of(String value, MenuNamePolicy menuNamePolicy) {
        MenuName menuName = new MenuName(value);
        menuNamePolicy.validate(menuName);

        return menuName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuName)) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
