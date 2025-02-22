package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuNameException;

import java.util.Objects;

public class MenuName {
    private final String value;

    public MenuName(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMenuNameException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
