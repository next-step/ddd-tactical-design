package kitchenpos.menus.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupNameException;

import java.util.Objects;

public class MenuGroupName {
    private final String name;

    public MenuGroupName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new InvalidMenuGroupNameException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
