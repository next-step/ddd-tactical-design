package kitchenpos.menu.domain.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuGroupName;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @EmbeddedId
    private MenuGroupId menuGroupId;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {}

    public MenuGroup(MenuGroupId menuGroupId, MenuGroupName name) {
        this.menuGroupId = menuGroupId;
        this.name = name;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public MenuGroupName getName() {
        return name;
    }
}
