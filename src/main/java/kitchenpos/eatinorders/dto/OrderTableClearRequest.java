package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderTableClearRequest extends DTO {
    @NotNull(message = "변경하려는 테이블을 선택해 주세요.")
    private final UUID uuid;

    public OrderTableClearRequest(UUID uuid) {
        this.uuid = uuid;
    }

    public OrderTableId getId() {
        return new OrderTableId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTableClearRequest that = (OrderTableClearRequest) o;

        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
