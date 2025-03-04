package kitchenpos.order.common.application.dto;

import java.util.UUID;
import kitchenpos.order.eatin.domain.model.OrderTableVo;

public record OrderTableResponse() {
    public record GetOrderTable(
        UUID id,
        String name,
        int numberOfGuests,
        boolean occupied
    ) {
        public static GetOrderTable fromVo(OrderTableVo.OrderTableInfo vo) {
            return new GetOrderTable(
                        vo.getOrderTableId(),
                        vo.getOrderTableName(),
                        vo.getNumberOfGuests(),
                        vo.occupied()
            );
        }
    }
}
