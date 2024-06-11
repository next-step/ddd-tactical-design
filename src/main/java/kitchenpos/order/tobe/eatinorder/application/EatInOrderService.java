package kitchenpos.order.tobe.eatinorder.application;

import kitchenpos.order.tobe.eatinorder.application.dto.request.EatInOrderCreateRequest;
import kitchenpos.order.tobe.eatinorder.domain.*;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository;
import kitchenpos.order.tobe.eatinorder.domain.validate.MenuValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final OrderTableRepository orderTableRepository;
    private final MenuValidator menuValidator;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final OrderTableRepository orderTableRepository,
            final MenuValidator menuValidator
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuValidator = menuValidator;
    }

    @Transactional
    public EatInOrder create(final EatInOrderCreateRequest request) {
        final List<OrderLineItem> orderLineItems = request.orderLineItems().stream()
                .map(dto -> new OrderLineItem(dto.menuId(), dto.quantity(), dto.price()))
                .toList();

        OrderLineItems validatedOrderLineItems = new OrderLineItems(orderLineItems, menuValidator);
        OrderTable orderTable = findOrderTableById(request.orderTableId());

        EatInOrder order = new EatInOrder(UUID.randomUUID(), validatedOrderLineItems, orderTable);
        return eatInOrderRepository.save(order);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);

        order.accept();

        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);

        order.serve();

        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);
        boolean allOrdersCompletedForTable = !eatInOrderRepository.existsByOrderTableAndStatusNot(order.getOrderTable(), EatInOrderStatus.COMPLETED);

        order.complete(allOrdersCompletedForTable);

        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    private OrderTable findOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }

    private EatInOrder findOrderById(UUID orderId) {
        return eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

}
