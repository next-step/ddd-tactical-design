package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupDisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupDisplayedName(name);
    }


    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public MenuGroupDisplayedName getName() {
        return name;
    }
}
