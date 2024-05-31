package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.exception.MenuNameProfanityException;

import java.util.Objects;

@Embeddable
public class MenuName {
    private static final String MENU_NAME_NULL_OR_BLANK_MESSAGE = "메뉴 이름은 비워둘 수 없습니다.";

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(final String name) {
        this.name = name;
        checkNullOrBlank(name);
    }

    public MenuName(final String name, ProfanityChecker profanityChecker) {
        this(name);
        containsProfanity(name, profanityChecker);
    }

    public static MenuName from(String name) {
        return new MenuName(name);
    }

    public static MenuName from(String name, ProfanityChecker profanityChecker) {
        return new MenuName(name, profanityChecker);
    }

    public String nameValue() {
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

    private void checkNullOrBlank(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException(MENU_NAME_NULL_OR_BLANK_MESSAGE);
        }
    }

    private void containsProfanity(String name, ProfanityChecker profanityChecker) {
        if (profanityChecker.containsProfanity(name)) {
            throw new MenuNameProfanityException(name);
        }
    }
}
