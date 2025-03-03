package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public class OrderTableId implements Serializable {

    private static final long serialVersionUID = -161403658033403389L;

    private UUID id;

    protected OrderTableId() {}

    public OrderTableId(UUID id) {
        this.id = id;
    }

    public static OrderTableId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString());
        }
        return new OrderTableId(id);
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
        OrderTableId orderTableId = (OrderTableId) o;
        return Objects.equals(id, orderTableId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
