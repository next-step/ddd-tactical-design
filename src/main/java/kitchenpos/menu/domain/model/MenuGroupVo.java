package kitchenpos.menu.domain.model;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuGroup;

public record MenuGroupVo() {

    public record GroupInfo(
        MenuGroupId id,
        MenuGroupName name
    ) {
        public static GroupInfo fromEntity(MenuGroup entity) {
            return new GroupInfo(entity.getMenuGroupId(), entity.getName());
        }

        public UUID getMenuGroupId() {
            return id.get();
        }

        public String getMenuGroupName() {
            return name.get();
        }
    }

    public record Create(String name) {}
}
