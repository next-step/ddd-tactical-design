package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.common.domain.model.OrderStatus;
import kitchenpos.common.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.validator.OrderValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderFixture {

    public static Order ORDER_FIXTURE(OrderType orderType, OrderStatus orderStatus, List<OrderLineItem> orderLineItems, UUID orderTableId, OrderValidator orderValidator) {
        return new Order(orderType, orderStatus, LocalDateTime.now(), orderLineItems, orderTableId, orderValidator);
    }

}
