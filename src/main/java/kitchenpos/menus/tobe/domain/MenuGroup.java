package kitchenpos.menus.tobe.domain;

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
    private MenuDisplayedName name;

    public MenuGroup() {
    }

    public MenuGroup(String name, MenuProfanityClient menuProfanityClient) {
        this(new MenuDisplayedName(name, menuProfanityClient));
    }

    public MenuGroup(MenuDisplayedName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public MenuDisplayedName getName() {
        return name;
    }
}
