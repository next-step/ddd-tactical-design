package kitchenpos.menus.ui.dto;

import java.util.Objects;

public class CreateMenuGroupRequest {
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateMenuGroupRequest that = (CreateMenuGroupRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
