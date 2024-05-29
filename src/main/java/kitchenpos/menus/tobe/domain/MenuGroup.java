package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

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
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = new MenuGroupName(name);
    }

    public String getName() {
        return name.getValue();
    }
}
