package kitchenpos.menus.menugroup.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName menuGroupName;

    protected MenuGroup() {
    }

    private MenuGroup(final UUID id, final MenuGroupName menuGroupName) {
        this.id = id;
        this.menuGroupName = menuGroupName;
    }

    public static MenuGroup create(final String name) {
        return new MenuGroup(UUID.randomUUID(), MenuGroupName.valueOf(name));
    }

    public UUID id() {
        return id;
    }

    public MenuGroupName menuGroupName() {
        return menuGroupName;
    }
}
