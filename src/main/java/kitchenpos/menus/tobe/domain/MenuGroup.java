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
    private Name name;

    public MenuGroup() {}

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = new Name(name);
    }

    public UUID getId() {
        return id;
    }
}
