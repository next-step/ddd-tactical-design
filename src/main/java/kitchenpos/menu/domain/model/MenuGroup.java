package kitchenpos.menu.domain.model;

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
        validator.validate(name);
        return new MenuGroup(id, MenuGroupName.of(name));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }
}
