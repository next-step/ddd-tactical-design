package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuName {

    @Column(name = "name")
    private String name;

    public MenuName(String name) {
        this.name = name;
    }

    protected MenuName() {

    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuName menuName = (MenuName) o;

        return name != null ? name.equals(menuName.name) : menuName.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
