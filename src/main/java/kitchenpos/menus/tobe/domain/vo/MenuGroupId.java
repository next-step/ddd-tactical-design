package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupIdException;

import java.util.Objects;
import java.util.UUID;

public class MenuGroupId {

    private final UUID value;

    public MenuGroupId(final String string) {
        this(UUID.fromString(string));
    }

    public MenuGroupId(final UUID value) {
        if (Objects.isNull(value)) {
            throw new InvalidMenuGroupIdException();
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
