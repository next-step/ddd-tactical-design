package kitchenpos.menus.tobe.menu.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuId implements Serializable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    protected MenuId() {
    }

    public MenuId(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuId menuId = (MenuId) o;
        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
