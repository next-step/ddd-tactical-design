package kitchenpos.menus.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    private MenuName(final String name) {
        this.name = name;
    }

    public static MenuName of(final String name, MenuPurgomalumClient menuPurgomalumClient) {
        validateName(name, menuPurgomalumClient);
        return new MenuName(name);
    }

    private static void validateName(final String name, MenuPurgomalumClient menuPurgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴명이 없습니다.");
        }
        if (menuPurgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴명에 비속어가 포함되었습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName that = (MenuName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
