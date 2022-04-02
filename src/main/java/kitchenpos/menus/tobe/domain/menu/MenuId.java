package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import java.util.UUID;

public final class MenuId {

    private final UUID value;

    public MenuId(UUID value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("id는 null 일 수 없습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuId)) {
            return false;
        }
        MenuId menuId = (MenuId) o;
        return Objects.equals(value, menuId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MenuId{" +
            "value=" + value +
            '}';
    }
}
