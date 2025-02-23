package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupIdException;

import java.util.Objects;
import java.util.UUID;

public class MenuGroupId {
    private final UUID id;


    public MenuGroupId(final String string) {
        this(UUID.fromString(string));
    }

    public MenuGroupId(final UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidMenuGroupIdException();
        }
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
