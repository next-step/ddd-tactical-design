package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    @OneToMany(mappedBy = "menuGroup")
    private List<Menu> menus = new ArrayList<>();

    protected MenuGroup() { }

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = new MenuGroupName(name);
    }

    public static MenuGroup of(MenuGroup request) {
        return new MenuGroup(UUID.randomUUID(), request.name.value());
    }

    public UUID getId() {
        return id;
    }
}
