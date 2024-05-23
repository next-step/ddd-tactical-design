package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    public MenuGroupName() {}

    public MenuGroupName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName menuGroupName1 = (MenuGroupName) o;
        return Objects.equals(name, menuGroupName1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
