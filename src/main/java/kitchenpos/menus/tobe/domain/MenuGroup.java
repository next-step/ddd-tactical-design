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
    private DisplayedName name;

    public MenuGroup() {
    }

    public MenuGroup(UUID id, DisplayedName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }
}
