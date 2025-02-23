package kitchenpos.menu.domain.model;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuGroup;

public record MenuGroupVo() {

    public record GroupInfo(
        UUID id,
        MenuGroupName name
    ) {
        public static GroupInfo fromEntity(MenuGroup entity) {
            return new GroupInfo(entity.getId(), entity.getName());
        }
    }

    public record Create(String name) {}
}
