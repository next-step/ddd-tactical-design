package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(MenuGroupName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public MenuGroupName name() {
        return name;
    }

    public String nameValue() {
        return name.name();
    }

}
