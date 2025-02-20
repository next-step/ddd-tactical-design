package kitchenpos.menu.domain.model;

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

    public MenuGroup(MenuGroupName name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public MenuGroup(String name, UUID id) {
        this.name = new MenuGroupName(name);
        this.id = id;
    }

    public MenuGroup(String name) {
        this(name, UUID.randomUUID());
    }

    public MenuGroup(MenuGroupName name) {
        this(name, UUID.randomUUID());
    }

    protected MenuGroup() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getValue();
    }
}
