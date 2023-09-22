package kitchenpos.menus.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    private MenuGroupName(final String name) {
        this.name = name;
    }

    public static MenuGroupName of(final String name) {
        validateName(name);
        return new MenuGroupName(name);
    }

    private static void validateName(final String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹명이 입력되지 않았습니다.");
        }
    }

    public String getName() {
        return name;
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
}
