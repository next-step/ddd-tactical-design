package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.menu.MenuManager;
import kitchenpos.eatinorders.tobe.domain.menu.Menus;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import kitchenpos.eatinorders.tobe.exception.IllegalOrderException;
import kitchenpos.eatinorders.tobe.exception.IllegalOrderTableException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Component
public class OrderValidateService {
    private final OrderTableManager orderTableManager;
    private final MenuManager menuManager;

    public OrderValidateService(final OrderTableManager orderTableManager, final MenuManager menuManager) {
        this.orderTableManager = orderTableManager;
        this.menuManager = menuManager;
    }

    public void validateOrder(final Order order) {
        final OrderType type = order.getType();
        final String deliveryAddress = order.getDeliveryAddress();
        if (Objects.isNull(type)) {
            throw new IllegalOrderException("type 은 필수값입니다.");
        }
        if (type == DELIVERY && (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty())) {
            throw new IllegalOrderException("배달 주문은 deliveryAddress 가 필수값이어야 합니다.");
        }
        if (type == EAT_IN) {
            validateOrderTable(order.getOrderTableId());
        }
        Menus.from(menuManager, order);
    }

    private void validateOrderTable(final UUID orderTableId) {
        final boolean isTableEmpty = orderTableManager.getOrderTable(orderTableId).isEmpty();
        if (isTableEmpty) {
            throw new IllegalOrderTableException("비어있는 테이블에는 주문을 추가할 수 없습니다.");
        }
    }
}
