package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;
import kitchenpos.shared.domain.Profanities;

import java.util.Objects;

public class MenuGroupName {
    private final String name;

    private MenuGroupName(final String name) {
        this.name = name;
    }

    public static MenuGroupName of(
            final String name,
            final Profanities profanities
    ) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new MenuGroupNameValidationException("메뉴 그룹 이름을 입력하세요");
        }
        if (profanities.contains(name)) {
            throw new MenuGroupNameValidationException("메뉴 그룹 이름에 비속어가 포함되어 있습니다. name=" + name);
        }
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
