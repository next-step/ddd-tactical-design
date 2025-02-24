package kitchenpos.order.eatinorder.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.common.model.OrderLineItemValidator;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderFactory {

    private final OrderLineItemValidator orderLineItemValidator;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderFactory(OrderLineItemValidator orderLineItemValidator, OrderTableRepository orderTableRepository) {
        this.orderLineItemValidator = orderLineItemValidator;
        this.orderTableRepository = orderTableRepository;
    }

    public EatInOrder create(List<OrderLineItem> orderLineItems, UUID orderTableId) {
        orderLineItemValidator.validate(orderLineItems);

        EatInOrder eatInOrder = new EatInOrder(LocalDateTime.now(),
                orderLineItems, EatInOrderFlow.WAITING);

        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.occupyOrderTable(orderTable);
        return eatInOrder;
    }
}
