package kitchenpos.order.domain.fixture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.order.common.application.dto.OrderRequest;
import kitchenpos.order.common.application.dto.OrderRequest.OrderLineItemCreate;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import org.flywaydb.core.internal.util.CollectionsUtils;

public record OrderFixture(UUID id,
                           OrderType 주문유형,
                           OrderStatus 주문상태,
                           LocalDateTime 주문시간,
                           List<OrderLineItemCreate> 주문아이템,
                           String 배달지주소,
                           UUID 주문테이블아이디) {

    public static final OrderType DEFAULT_ORDER_TYPE = OrderType.DELIVERY;
    public static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.WAITING;
    public static final String DEFAULT_DELIVERY_ADDRESS = "성남시 분당구 삼평동";
    public static final LocalDateTime DEFAULT_ORDER_TIME = LocalDateTime.now();

    public static OrderFixture init() {
        return new OrderFixture(
            UUID.randomUUID(),
            DEFAULT_ORDER_TYPE,
            DEFAULT_ORDER_STATUS,
            DEFAULT_ORDER_TIME,
            List.of(OrderLineItemFixture.init(null).create()),
            DEFAULT_DELIVERY_ADDRESS,
            OrderTableFixture.init().toEntity().getOrderTableId().get()
        );
    }

    public static OrderFixture test(OrderType 주문유형,
        OrderStatus 주문상태,
        LocalDateTime 주문시간,
        List<OrderLineItemCreate> 주문아이템,
        String 배달지주소,
        UUID 주문테이블아이디) {
        return new OrderFixture(
            UUID.randomUUID(),
            Objects.requireNonNullElse(주문유형, DEFAULT_ORDER_TYPE),
            Objects.requireNonNullElse(주문상태, DEFAULT_ORDER_STATUS),
            Objects.requireNonNullElse(주문시간, DEFAULT_ORDER_TIME),
            주문아이템,
            배달지주소,
            주문테이블아이디
        );
    }

    public Order toEntity() {
        return new Order(
            OrderId.of(id),
            주문유형,
            주문상태,
            주문시간,
            new OrderLineItems(주문아이템.stream()
            .map(주문항목 -> OrderLineItem.fromDto(주문항목, OrderId.of(id)))
            .collect(Collectors.toList())),
            OrderTableId.of(주문테이블아이디)
        );
    }

    public OrderRequest.Create create() {
        return new OrderRequest.Create(주문유형,  주문테이블아이디, 주문아이템, 배달지주소);
    }
    public OrderVo.Create createVo() {
        return new OrderVo.Create(
            주문유형,
            OrderTableId.of(주문테이블아이디),
            OrderLineItems.of(
                CollectionsUtils.hasItems(주문아이템) ?
                주문아이템.stream()
                .map(주문항목 -> OrderLineItem.fromDto(주문항목, OrderId.of(id)))
                .collect(Collectors.toList()) : null),
            배달지주소);
    }

}

