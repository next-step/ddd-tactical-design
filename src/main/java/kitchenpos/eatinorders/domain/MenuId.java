package kitchenpos.eatinorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuId {

    @Column(name = "menu_id", nullable = false)
    private UUID id;

    protected MenuId() {

    }

    public MenuId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuId menuId = (MenuId) o;

        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
