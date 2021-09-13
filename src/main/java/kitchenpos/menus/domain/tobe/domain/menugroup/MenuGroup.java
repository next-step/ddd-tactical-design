package kitchenpos.menus.domain.tobe.domain.menugroup;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.MenuGroupId;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private MenuGroupId id;

    @Embedded
    @Column(name = "displayed_name", nullable = false)
    private DisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(final MenuGroupId id, final DisplayedName name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
