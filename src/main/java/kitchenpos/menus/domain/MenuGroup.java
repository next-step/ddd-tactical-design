package kitchenpos.menus.domain;

import java.util.Objects;
import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(MenuGroupName name) {
        this(UUID.randomUUID(), name);
    }

    public MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return this.name.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
