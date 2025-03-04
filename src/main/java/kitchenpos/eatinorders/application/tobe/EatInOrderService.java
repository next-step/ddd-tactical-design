package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.presentation.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class EatInOrderService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemsValidator orderLineItemsValidator;
    private final ClearOrderTableService clearOrderTableService;

    public EatInOrderService(OrderRepository orderRepository, OrderTableRepository orderTableRepository, OrderLineItemsValidator orderLineItemsValidator, ClearOrderTableService clearOrderTableService) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemsValidator = orderLineItemsValidator;
        this.clearOrderTableService = clearOrderTableService;
    }

    @Transactional
    public EatInOrderCreateResponse create(final EatInOrderCreateRequest request) {
        orderLineItemsValidator.validate(request.getOrderLineItems());

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        OrderEntity orderEntity = EatInOrderCreateRequest.toEntity(request);
        EatInOrder eatInOrder = new EatInOrder(orderEntity);
        eatInOrder.createOrder(orderTable);

        orderRepository.save(eatInOrder.toEntity());
        return EatInOrderCreateResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderAcceptResponse accept(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(order);
        eatInOrder.accept();

        return EatInOrderAcceptResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderServedResponse serve(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(order);
        eatInOrder.serve();

        return EatInOrderServedResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderCompletedResponse complete(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(order);
        eatInOrder.complete();

        clearOrderTableService.clearOrderTable(order.id());
        return EatInOrderCompletedResponse.from(eatInOrder);
    }
}
