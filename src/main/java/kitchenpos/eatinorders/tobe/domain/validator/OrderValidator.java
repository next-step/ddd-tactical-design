package kitchenpos.eatinorders.tobe.domain.validator;

import kitchenpos.common.domain.model.OrderStatus;
import kitchenpos.common.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.support.DomainService;

import java.math.BigDecimal;
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

    private boolean containsUnderZeroQuantity(OrderLineItems orderLineItems) {
        return orderLineItems.getOrderLineItems().stream()
                .anyMatch(orderLineItem -> orderLineItem.getOrderLineItemQuantity().getQuantity().compareTo(BigDecimal.ZERO) <= 0);
    }
}

