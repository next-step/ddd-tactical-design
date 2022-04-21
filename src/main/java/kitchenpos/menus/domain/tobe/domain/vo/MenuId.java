package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class MenuId extends Id {
    @Column(name = "id")
    private UUID id;

    public MenuId(UUID id) {
        this.id = id;
    }

    protected MenuId() {
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MenuId menuId = (MenuId) o;

        return id != null ? id.equals(menuId.id) : menuId.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
