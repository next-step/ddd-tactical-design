package kitchenpos.menu.domain.model;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private final UUID id;
    private final MenuGroupName name;

    private MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(
            final UUID id,
            final String name,
            final ProfanityFilteringMenuGroupNameValidator validator) {
        if (id == null) {
            throw new IllegalArgumentException("메뉴 그룹 ID가 Null 입니다.");
        }
        return new MenuGroup(id, MenuGroupName.of(name, validator));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
