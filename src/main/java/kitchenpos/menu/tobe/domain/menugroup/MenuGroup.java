package kitchenpos.menu.tobe.domain.menugroup;


import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    private MenuGroup(UUID uuid, MenuGroupName name) {
        this.id = uuid;
        this.name = name;
    }

    public static MenuGroup of(String name) {
        return new MenuGroup(UUID.randomUUID(), MenuGroupName.of(name));
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public MenuGroupName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
