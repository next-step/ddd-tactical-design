package kitchenpos.order.eatin.domain.model;

import java.util.UUID;
import kitchenpos.order.eatin.domain.entity.OrderTable;

public record OrderTableVo() {

    public record OrderTableInfo(
        OrderTableId id,
        OrderTableName name,
        OrderTableGuests numberOfGuests,
        boolean occupied
    ) {
        public static OrderTableInfo fromEntity(OrderTable entity) {
            return new OrderTableInfo(
                entity.getOrderTableId(),
                entity.getName(),
                entity.getNumberOfGuests(),
                entity.isOccupied()
            );
        }

        public UUID getOrderTableId() {
            return id.get();
        }

        public String getOrderTableName() {
            return name.get();
        }

        public int getNumberOfGuests() {
           return numberOfGuests.get();
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
