package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private UUID id;
    private String name;

    public MenuGroup(UUID id, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", name));
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id) && Objects.equals(name, menuGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
