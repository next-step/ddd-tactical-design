package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(UUID uuid, MenuGroupName name) {
        this.id = uuid;
        this.name = name;
    }

    public MenuGroup(MenuGroupName name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public MenuGroupName getName() {
        return name;
    }
}
