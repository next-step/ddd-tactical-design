package kitchenpos.menus.tobe.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup extends AbstractAggregateRoot<MenuGroup> {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {

    }

    protected MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(final MenuGroupName name) {
        return new MenuGroup(
                UUID.randomUUID(),
                name
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
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
