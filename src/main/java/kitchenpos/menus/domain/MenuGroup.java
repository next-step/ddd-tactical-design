package kitchenpos.menus.domain;

import kitchenpos.common.values.Name;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    protected MenuGroup() {
    }

    public MenuGroup(final Name name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

}
