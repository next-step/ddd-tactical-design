package kitchenpos.menus.domain.tobe.menugroup;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public MenuGroup(String name) {
        this(UUID.randomUUID(), new MenuGroupName(name));
    }

    public MenuGroup(MenuGroupName name) {
        this(UUID.randomUUID(), name);
    }

    public MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
