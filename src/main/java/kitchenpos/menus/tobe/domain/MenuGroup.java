package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.ui.MenuGroupRequest;

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

    public static MenuGroup of(MenuGroupRequest request) {
        return new MenuGroup(UUID.randomUUID(), request.getName());
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name.value();
    }
}
