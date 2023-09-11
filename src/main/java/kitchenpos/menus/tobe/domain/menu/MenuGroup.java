package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroupDisplayedName;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupDisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupDisplayedName(name);
    }


    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public MenuGroupDisplayedName getName() {
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
        return id != null ? id.hashCode() : 0;
    }
}
