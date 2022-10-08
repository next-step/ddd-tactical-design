package kitchenpos.menus.domain;

import kitchenpos.menus.domain.vo.MenuGroupName;

import javax.persistence.Column;
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

    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = new MenuGroupName(name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

}
