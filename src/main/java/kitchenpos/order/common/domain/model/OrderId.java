package kitchenpos.order.common.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public class OrderId implements Serializable {

    private static final long serialVersionUID = -161403658033403389L;
    private UUID id;

    protected OrderId() {}

    public OrderId(UUID id) {
        this.id = id;
    }

    public static OrderId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString());
        }
        return new OrderId(id);
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
        OrderId menuId = (OrderId) o;
        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
