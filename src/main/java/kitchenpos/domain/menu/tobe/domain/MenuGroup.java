package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        this(UUID.randomUUID(), name);
    }

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = new MenuGroupName(name);
    }

    public String name() {
        return name.getName();
    }
}
