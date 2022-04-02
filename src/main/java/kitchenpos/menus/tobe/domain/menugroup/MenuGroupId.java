package kitchenpos.menus.tobe.domain.menugroup;

import java.util.Objects;
import java.util.UUID;

public final class MenuGroupId {
    private final UUID value;

    public MenuGroupId(UUID value) {
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
        if (!(o instanceof MenuGroupId)) {
            return false;
        }
        MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MenuGroupId{" +
            "value=" + value +
            '}';
    }
}
