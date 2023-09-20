package kitchenpos.menus.tobe.domain.menugroup;

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
    private MenuGroupName name;

    public MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    protected MenuGroup() {
    }

    public static MenuGroup from(MenuGroupName name) {
        return new MenuGroup(UUID.randomUUID(), name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getMenuGroupName();
    }
}
