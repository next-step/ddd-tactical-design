package kitchenpos.order.domain.fixture;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.order.common.application.dto.OrderRequest.OrderLineItemCreate;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;
import kitchenpos.order.delivery.domain.model.DeliveryInfo;

public record DeliveryOrderFixture(UUID id,
                                   OrderType 주문유형,
                                   OrderStatus 주문상태,
                                   List<OrderLineItemCreate> 주문아이템,
                                   String 배달지주소) {

    public static final OrderType DEFAULT_ORDER_TYPE = OrderType.DELIVERY;
    public static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.WAITING;
    public static final String DEFAULT_DELIVERY_ADDRESS = "성남시 분당구 삼평동";
    public static DeliveryOrderFixture init() {
        return new DeliveryOrderFixture(
            UUID.randomUUID(),
            DEFAULT_ORDER_TYPE,
            DEFAULT_ORDER_STATUS,
            List.of(OrderLineItemFixture.init(null, DEFAULT_ORDER_TYPE).create()),
            DEFAULT_DELIVERY_ADDRESS
        );
    }

    public static DeliveryOrderFixture test(
        OrderType 주문유형,
        OrderStatus 주문상태,
        List<OrderLineItemCreate> 주문아이템,
        String 배달지주소) {
        return new DeliveryOrderFixture(
            UUID.randomUUID(),
            주문유형,
            Objects.requireNonNullElse(주문상태, DEFAULT_ORDER_STATUS),
            Objects.requireNonNullElse(주문아이템, List.of(OrderLineItemFixture.init(null, 주문유형).create())),
            Objects.requireNonNullElse(배달지주소, DEFAULT_DELIVERY_ADDRESS)
        );
    }

    public DeliveryOrder toEntity() {
        return new DeliveryOrder(
            OrderId.of(id),
            주문유형,
            주문상태,
            new OrderLineItems(주문아이템.stream()
            .map(주문항목 -> OrderLineItem.fromDto(주문항목, OrderId.of(id), 주문유형))
            .collect(Collectors.toList())),
            DeliveryInfo.of(배달지주소)
        );
    }
}

