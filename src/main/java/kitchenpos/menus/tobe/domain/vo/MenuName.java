package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuNameException;
import kitchenpos.menus.tobe.domain.exception.MenuNameContainsProfanityException;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuName that = (MenuName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
