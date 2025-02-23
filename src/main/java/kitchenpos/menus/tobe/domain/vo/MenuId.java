package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuIdException;

import java.util.Objects;
import java.util.UUID;

public class MenuId {
    private final UUID value;

    public MenuId() {
        this(UUID.randomUUID());
    }

    public MenuId(final String value) {
        this(UUID.fromString(value));
    }

    public MenuId(final UUID value) {
        if(Objects.isNull(value)) {
            throw new InvalidMenuIdException();
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
        final MenuId that = (MenuId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
