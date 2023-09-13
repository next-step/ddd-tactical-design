package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuGroupNameException;
import kitchenpos.support.ValueObject;

import java.util.Objects;

public class MenuGroupName extends ValueObject {
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
