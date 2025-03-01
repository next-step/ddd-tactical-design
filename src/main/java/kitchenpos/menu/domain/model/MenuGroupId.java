package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public class MenuGroupId implements Serializable {

    private static final long serialVersionUID = 5221255306203664019L;

    private UUID id;

    protected MenuGroupId() {}

    public MenuGroupId(UUID id) {
        this.id = id;
    }

    public static MenuGroupId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU_GROUP.toString());
        }
        return new MenuGroupId(id);
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
        MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
