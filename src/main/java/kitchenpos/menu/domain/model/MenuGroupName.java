package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;

import java.util.Objects;

public class MenuGroupName {
    private final String name;

    private MenuGroupName(final String name) {
        this.name = name;
    }

    public static MenuGroupName of(
            final String name,
            final ProfanityFilteringMenuGroupNameValidator validator
    ) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new MenuGroupNameValidationException("메뉴 그룹 이름을 입력하세요");
        }
        validator.validate(name);
        return new MenuGroupName(name);
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
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
