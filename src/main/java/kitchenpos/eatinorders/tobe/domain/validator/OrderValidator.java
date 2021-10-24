package kitchenpos.eatinorders.tobe.domain.validator;

import kitchenpos.common.domain.model.OrderStatus;
import kitchenpos.common.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.support.DomainService;

import java.util.UUID;

@DomainService
public class OrderValidator {

    private final OrderTableRepository orderTableRepository;

    public OrderValidator(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public void checkTableStatus(UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테이블입니다."));
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException("테이블이 세팅되지 않았습니다.");
        }
    }

    public void checkOrderLineItemQuantity(OrderLineItems orderLineItems, OrderType orderType) {
        if (orderType != OrderType.EAT_IN && containsUnderZeroQuantity(orderLineItems)) {
            throw new IllegalArgumentException("매장 주문만 0 미만의 수량을 가질 수 있습니다.");
        }
    }

    public void checkEmptyOrderLineItems(OrderLineItems orderLineItems) {
        if (orderLineItems.getOrderLineItems().isEmpty()) {
            throw new IllegalArgumentException("메뉴가 없으면 등록할 수 없습니다.");
        }
    }

    public void advanceOrderStatus(Order order) {
        OrderStatus orderStatus = order.getOrderStatus();

        if (orderStatus == OrderStatus.WAITING) {
            order.setOrderStatus(OrderStatus.ACCEPTED);
            return;
        }

        if (orderStatus == OrderStatus.ACCEPTED) {
            order.setOrderStatus(OrderStatus.SERVED);
            return;
        }

        if (orderStatus == OrderStatus.SERVED) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            return;
        }

        if (orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문입니다.");
        }
    }

    private boolean containsUnderZeroQuantity(OrderLineItems orderLineItems) {
        return orderLineItems.containsUnderZeroQuantity();
    }
}

