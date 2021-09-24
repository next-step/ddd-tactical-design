package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.menu.MenuDomainService;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.*;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Component
public class OrderDomainService {
    private final OrderTableManager orderTableManager;
    private final MenuDomainService menuDomainService;

    public OrderDomainService(final OrderTableManager orderTableManager, final MenuDomainService menuDomainService) {
        this.orderTableManager = orderTableManager;
        this.menuDomainService = menuDomainService;
    }

    public OrderTable getOrderTable(final Order order) {
        return orderTableManager.getOrderTable(order.getOrderTableId());
    }

    public void validateOrder(final Order order) {
        final OrderType type = order.getType();
        final String deliveryAddress = order.getDeliveryAddress();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("type 은 필수값입니다.");
        }
        if (type == DELIVERY && (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty())) {
            throw new IllegalArgumentException("배달 주문은 deliveryAddress 가 필수값이어야 합니다.");
        }
        if (type == EAT_IN) {
            validateOrderTable(order.getOrderTableId());
        }
        menuDomainService.validateOrder(order);
    }

    private void validateOrderTable(final UUID orderTableId) {
        final boolean isTableEmpty = orderTableManager.getOrderTable(orderTableId).isEmpty();
        if (isTableEmpty) {
            throw new IllegalStateException("비어있는 테이블에는 주문을 추가할 수 없습니다.");
        }
    }

    public void acceptOrder(final Order order) {
        menuDomainService.deliver(order);
        order.changeStatus(ACCEPTED);
    }

    public void serveOrder(final Order order) {
        if (order.getStatus() != ACCEPTED) {
            throw new IllegalStateException("접수되지 않은 주문은 서빙할 수 없습니다.");
        }
        order.changeStatus(SERVED);
    }

    public void startDelivery(final Order order) {
        if (order.getType() != DELIVERY) {
            throw new IllegalStateException("배달 타입이 아닌 주문은 배달할 수 없습니다.");
        }
        if (order.getStatus() != SERVED) {
            throw new IllegalStateException("서빙되지 않은 주문은 배달할 수 없습니다.");
        }
        order.changeStatus(DELIVERING);
    }

    public void completeDelivery(final Order order) {
        if (order.getStatus() != DELIVERING) {
            throw new IllegalStateException("배달 중이지 않은 주문은 배달 완료할 수 없습니다.");
        }
        order.changeStatus(DELIVERED);
    }

    public void completeOrder(final Order order) {
        final OrderType type = order.getType();
        final OrderStatus status = order.getStatus();
        if (type == DELIVERY && status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("배달 완료된 주문은 다시 완료할 수 없습니다.");
        }
        if ((type == OrderType.TAKEOUT || type == EAT_IN) && status != SERVED) {
            throw new IllegalStateException("서빙되지 않은 주문은 완료할 수 없습니다.");
        }
        order.changeStatus(COMPLETED);
        if (type == EAT_IN) {
            orderTableManager.clearOrderTable(order.getOrderTableId());
        }
    }
}
