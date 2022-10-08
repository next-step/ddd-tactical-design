package kitchenpos.menus.domain.tobe;

import java.util.Objects;
import java.util.UUID;

public class MenuID {
    private final UUID value;

    public MenuID() {
        this.value = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuID menuID = (MenuID) o;

        return Objects.equals(value, menuID.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
