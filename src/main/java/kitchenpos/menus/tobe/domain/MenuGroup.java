package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity(name = "TobeMenuGroup")
public class MenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName menuGroupName;

    protected MenuGroup() {}

    private MenuGroup(final UUID id, final MenuGroupName menuGroupName) {
        this.id = id;
        this.menuGroupName = menuGroupName;
    }

    public MenuGroup(final UUID id, final String name) {
        this(id, new MenuGroupName(name));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return menuGroupName.getName();
    }
}
