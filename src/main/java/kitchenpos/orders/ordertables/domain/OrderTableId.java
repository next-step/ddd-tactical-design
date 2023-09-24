package kitchenpos.orders.ordertables.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class OrderTableId implements Serializable {

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    public OrderTableId() {
        this.id = UUID.randomUUID();
    }

    public OrderTableId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderTableId)) return false;

        OrderTableId eatInOrderId = (OrderTableId) o;

        return getId() != null ? getId().equals(eatInOrderId.getId()) : eatInOrderId.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
