package kitchenpos.menus.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    private MenuName(String name) {
        nullValidation(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MenuName menuName = (MenuName)o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static MenuName of(String name) {
        return new MenuName(name);
    }

    public static MenuName of(String name, boolean containsProfanity) {
        if (containsProfanity) {
            throw new IllegalArgumentException("상품 이름에 비속어가 포함되어 있습니다.");
        }
        return new MenuName(name);
    }

    private void nullValidation(String name) {
        if (name == "" || Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 필수로 입력되야 합니다.");
        }
    }
}
