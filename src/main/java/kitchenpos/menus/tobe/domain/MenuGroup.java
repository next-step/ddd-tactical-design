package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @EmbeddedId
    private MenuGroupId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(MenuGroupId id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupId getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }
}
