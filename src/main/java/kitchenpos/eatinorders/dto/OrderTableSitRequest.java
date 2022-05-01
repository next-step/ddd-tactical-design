package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderTableSitRequest extends DTO {
    @NotNull(message = "변경하려는 테이블을 입력해 주세요.")
    private final UUID uuid;

    public OrderTableSitRequest(UUID uuid) {
        this.uuid = uuid;
    }

    public OrderTableId getId() {
        return new OrderTableId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTableSitRequest that = (OrderTableSitRequest) o;

        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
