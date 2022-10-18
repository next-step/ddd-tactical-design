package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupDisplayedName name;

    public MenuGroup() {
    }

    public MenuGroup(UUID id, String name, MenuProfanityClient menuProfanityClient) {
        this(id, new MenuGroupDisplayedName(name, menuProfanityClient));
    }

    public MenuGroup(String name, MenuProfanityClient menuProfanityClient) {
        this(UUID.randomUUID(), new MenuGroupDisplayedName(name, menuProfanityClient));
    }

    public MenuGroup(UUID id, MenuGroupDisplayedName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public MenuGroupDisplayedName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroup menuGroup = (MenuGroup) o;

        if (!Objects.equals(id, menuGroup.id)) return false;
        return Objects.equals(name, menuGroup.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
