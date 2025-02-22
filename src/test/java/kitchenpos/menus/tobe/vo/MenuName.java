package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuNameException;
import kitchenpos.menus.tobe.exception.MenuNameContainsProfanityException;
import kitchenpos.products.tobe.domain.vo.Profanities;

import java.util.Objects;

public class MenuName {
    private final String value;

    public MenuName(final String value, final Profanities profanities) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMenuNameException();
        }
        if (profanities.contains(value)) {
            throw new MenuNameContainsProfanityException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
