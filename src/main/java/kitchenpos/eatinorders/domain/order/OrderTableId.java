package kitchenpos.eatinorders.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderTableId {

    @Column(name = "order_table_id", nullable = false)
    private UUID id;

    protected OrderTableId() {

    }

    public OrderTableId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTableId menuId = (OrderTableId) o;

        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
