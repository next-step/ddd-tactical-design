package kitchenpos.menugroups.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @EmbeddedId
    private MenuGroupId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupDisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        this.id = new MenuGroupId();
        this.name = new MenuGroupDisplayedName(name);
    }

    public MenuGroupId getId() {
        return id;
    }

    public UUID getIdValue() {
        return id.getValue();
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
