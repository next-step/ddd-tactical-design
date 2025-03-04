package kitchenpos.order.domain.fixture;

import java.util.UUID;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.model.OrderTableGuests;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableName;
import kitchenpos.order.eatin.domain.model.OrderTableVo;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Create;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Update;

public record OrderTableFixture(UUID id, String 테이블명, int 인원수, boolean 사용여부) {

    public static final String DEFAULT_ORDER_TABLE_NAME = "1번 테이블";
    public static final int DEFAULT_ORDER_TABLE_GUESTS = 3;
    public static final boolean DEFAULT_ORDER_TABLE_OCCUPIED = false;

    public static OrderTableFixture init() {
        return new OrderTableFixture(
            UUID.randomUUID(),
            DEFAULT_ORDER_TABLE_NAME,
            DEFAULT_ORDER_TABLE_GUESTS,
            DEFAULT_ORDER_TABLE_OCCUPIED
        );
    }

    public static OrderTableFixture test(String 테이블명, int 인원수, boolean 사용여부) {
        return new OrderTableFixture(
            UUID.randomUUID(),
            테이블명,
            인원수,
            사용여부
        );
    }

    public OrderTable toEntity() {
        return new OrderTable(OrderTableId.of(id), OrderTableName.of(테이블명), OrderTableGuests.of(인원수), 사용여부);
    }

    public OrderTableVo.Create create() {
        return new Create(OrderTableName.of(테이블명), OrderTableGuests.of(인원수), 사용여부);
    }

    public OrderTableVo.Update update() {
        return new Update(OrderTableId.of(id), OrderTableGuests.of(인원수));
    }
}

