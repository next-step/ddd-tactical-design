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
    private MenuGroupName menuGroupName;

    public MenuGroup() {}

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.menuGroupName = new MenuGroupName(name);
    }

    public UUID getId() {
        return id;
    }
}
