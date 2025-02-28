package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public class MenuId implements Serializable {

    private static final long serialVersionUID = -1938201557775291040L;
    private UUID id;

    protected MenuId() {}

    public MenuId(UUID id) {
        this.id = id;
    }

    public static MenuId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString());
        }
        return new MenuId(id);
    }


    public UUID get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuId menuId = (MenuId) o;
        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
