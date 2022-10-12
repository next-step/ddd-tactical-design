package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.DisplayedName;
import kitchenpos.common.Profanity;

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
    private DisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(Profanity profanity, String name) {
        this(new DisplayedName(profanity, name));
    }

    public MenuGroup(DisplayedName name) {
        this.name = name;
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
