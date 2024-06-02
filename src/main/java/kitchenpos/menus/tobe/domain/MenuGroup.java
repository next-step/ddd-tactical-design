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

    protected MenuGroup(String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public static MenuGroup create(String name) {
        return new MenuGroup(name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.name.getName();
    }

}
