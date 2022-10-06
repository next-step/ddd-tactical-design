package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Tobe_MenuGroup")
public class MenuGroup {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
    }

    public MenuGroup(Profanity profanity, String name) {
        this.name = new MenuGroupName(profanity, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroup menuGroup = (MenuGroup) o;
        return id.equals(menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
