package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class EatInOrderService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemsValidator orderLineItemsValidator;

    public EatInOrderService(OrderRepository orderRepository, OrderTableRepository orderTableRepository, OrderLineItemsValidator orderLineItemsValidator) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemsValidator = orderLineItemsValidator;
    }

    @Transactional
    public EatInOrderCreateResponse create(final EatInOrderCreateRequest request) {
        orderLineItemsValidator.validate(request.getOrderLineItems());

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        OrderEntity orderEntity = EatInOrderCreateRequest.toEntity(request);
        EatInOrder eatInOrder = new EatInOrder(orderEntity);
        eatInOrder.createOrder(orderTable);

        OrderEntity savedOrder = orderRepository.save(eatInOrder.toEntity());
        return EatInOrderCreateResponse.from(savedOrder);
    }
}
