package kitchenpos.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.exception.IllegalNameException;

import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    MenuName(String menuName) {
        validateName(menuName);
        this.name = menuName;
    }

    private static void validateName(String menuName) {
        if (Objects.isNull(menuName)) {
            throw new IllegalNameException("이름은 빈값일 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
