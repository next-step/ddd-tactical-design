package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuNameValidationException;

import java.util.Objects;

public class MenuName {
    private final String name;

    private MenuName(final String name) {
        this.name = name;
    }

    public static MenuName of(
            final String name,
            final ProfanityFilteringMenuNameValidator validator
    ) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new MenuNameValidationException("메뉴 이름을 입력하세요");
        }
        validator.validate(name);
        return new MenuName(name);
    }

    public boolean isSameName(String name) {
        if (this.name == null) {
            return false;
        }
        return this.name.equals(name);
    }

    public String value() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
