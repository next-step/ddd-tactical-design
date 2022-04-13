package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.support.dto.DTO;

import java.util.UUID;

public class MenuGroupRegisterResponse extends DTO {
    private UUID id;
    private String name;

    public MenuGroupRegisterResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupRegisterResponse(TobeMenuGroup menuGroup) {
        this(menuGroup.getId().getValue(), menuGroup.getName().getValue());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroupRegisterResponse that = (MenuGroupRegisterResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
