package kitchenpos.menus.tobe.domain.menugroup;

import kitchenpos.global.vo.DisplayedName;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    protected MenuGroup() {

    }

    public MenuGroup(DisplayedName name) {
        this.name = name;
    }

    public MenuGroup(UUID id, DisplayedName name) {
        this(name);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroup menuGroup = (MenuGroup) o;

        if (id != null ? !id.equals(menuGroup.id) : menuGroup.id != null) return false;
        return name != null ? name.equals(menuGroup.name) : menuGroup.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
