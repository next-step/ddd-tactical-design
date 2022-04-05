package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class MenuGroupId implements Serializable {
    @Column(name = "id")
    private UUID id;

    public MenuGroupId(UUID id) {
        this.id = id;
    }

    protected MenuGroupId() {
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroupId productId = (MenuGroupId) o;

        return id != null ? id.equals(productId.id) : productId.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
