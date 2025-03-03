package kitchenpos.order.eatin.domain.model;

import java.util.UUID;
import kitchenpos.order.eatin.domain.entity.OrderTable;

public record OrderTableVo() {

    public record OrderTableInfo(
        OrderTableId id
    ) {
        public static OrderTableInfo fromEntity(OrderTable entity) {
            return new OrderTableInfo(entity.getOrderTableId());
        }

        public UUID getOrderTableId() {
            return id.get();
        }

    }

    public record Create(
        OrderTableName name,
        OrderTableGuests guests,
        boolean occupied
    ) {


    }

    public record Update(OrderTableId orderTableId, OrderTableGuests guests) {}
}
