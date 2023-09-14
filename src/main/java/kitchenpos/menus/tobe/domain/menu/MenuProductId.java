package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProductId implements Serializable {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    protected MenuProductId() {

    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProductId menuId = (MenuProductId) o;

        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
