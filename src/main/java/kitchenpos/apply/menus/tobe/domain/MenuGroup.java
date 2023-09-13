package kitchenpos.apply.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() { }

    public static MenuGroup of(String name) {
        return new MenuGroup(name);
    }

    public MenuGroup(String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name.value();
    }
}
