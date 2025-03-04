package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.presentation.dto.*;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
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
        EatInOrder eatInOrder = EatInOrderCreateRequest.from(request);
        eatInOrder.createOrder(orderTable);

        orderRepository.save(eatInOrder.toEntity());
        return EatInOrderCreateResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderAcceptResponse accept(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(
                order.id(),
                order.status(),
                order.orderLineItems(),
                order.orderTableId(),
                order.orderDateTime()
        );
        eatInOrder.accept();

        return EatInOrderAcceptResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderServedResponse serve(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(
                order.id(),
                order.status(),
                order.orderLineItems(),
                order.orderTableId(),
                order.orderDateTime()
        );
        eatInOrder.serve();

        return EatInOrderServedResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderCompletedResponse complete(final OrderId orderId) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = new EatInOrder(
                order.id(),
                order.status(),
                order.orderLineItems(),
                order.orderTableId(),
                order.orderDateTime()
        );
        eatInOrder.complete();

        return EatInOrderCompletedResponse.from(eatInOrder);
    }
}
