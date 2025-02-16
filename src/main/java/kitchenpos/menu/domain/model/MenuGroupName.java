package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;

import java.util.Objects;

public class MenuGroupName {
    private final String name;

    private MenuGroupName(final String name) {
        this.name = name;
    }

    public static MenuGroupName of(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new MenuGroupNameValidationException("메뉴 그룹 이름을 입력하세요");
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
}
