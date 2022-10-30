package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.menus.tobe.domain.vo.MenuGroupName;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "productName", nullable = false))
    private MenuGroupName name;

    public UUID getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }

    public MenuGroup(MenuGroupName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    protected MenuGroup() {}
}
