package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
public class MenuGroupInfo {
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Transient
    private UUID menuGroupId;

    protected MenuGroupInfo() {}

    public MenuGroupInfo(final MenuGroup menuGroup, final UUID menuGroupId) {
        this.menuGroup = menuGroup;
        this.menuGroupId = menuGroupId;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
