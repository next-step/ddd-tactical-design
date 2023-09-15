package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId {

    @Column(name = "product_id", nullable = false)
    private UUID id;

    protected ProductId() {

    }

    public ProductId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductId menuId = (ProductId) o;

        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
