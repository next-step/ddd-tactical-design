package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, PurgomalumChecker purgomalumChecker) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("메뉴의 이름은 비어있을 수 없습니다. name: " + name);
        }

        if (purgomalumChecker.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴의 이름은 비속어가 포함될 수 없습니다. name: " + name);
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
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
