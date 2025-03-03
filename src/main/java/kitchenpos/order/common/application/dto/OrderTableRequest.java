package kitchenpos.order.common.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import kitchenpos.order.eatin.domain.model.OrderTableGuests;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableName;
import kitchenpos.order.eatin.domain.model.OrderTableVo;

public record OrderTableRequest() {

    public record Create(
        @NotNull(message = "테이블 이름은 필수입니다.")
        String name

    ) {
        public OrderTableVo.Create toVo() {
            return new OrderTableVo.Create(
                OrderTableName.of(name),
                OrderTableGuests.of(0),
                false
            );
        }
    }

    public record UpdateGuests(
        UUID orderTableId,
        @Positive(message = "테이블 인원 0보다 커야 합니다.")
        int numberOfGuests
    ) {

        public OrderTableVo.Update toVo() {
            return new OrderTableVo.Update(OrderTableId.of(orderTableId), OrderTableGuests.of(numberOfGuests));
        }
    }
}
