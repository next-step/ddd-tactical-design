package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class ProductInMenu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    protected ProductInMenu() {

    }

    protected ProductInMenu(UUID id) {
        this.id = id;
    }

    public static ProductInMenu create(UUID productId) {
        return new ProductInMenu(productId);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInMenu that = (ProductInMenu) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
