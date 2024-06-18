package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.dto.request.EatInOrderCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TobeOrderService {
    private final EatInOrderRepository orderRepository;
    private final TobeOrderTableService tobeOrderTableService;

    private final MenuDomainSupport menuDomainSupport;

    public TobeOrderService(
            final EatInOrderRepository orderRepository,
            final TobeOrderTableService tobeOrderTableService,
            final MenuDomainSupport menuDomainSupport
    ) {
        this.orderRepository = orderRepository;
        this.tobeOrderTableService = tobeOrderTableService;
        this.menuDomainSupport = menuDomainSupport;
    }

    @Transactional
    public EatInOrder create(final EatInOrderCreateRequest request) {
        final List<EatInOrderCreateRequest.OrderLineItemRequest> orderLineItemRequests = request.orderLineItemRequests();
        final UUID orderTableId = request.orderTableId();

        List<EatInOrderLineItem> eatInOrderLineItems = orderLineItemRequests
                .parallelStream()
                .map(item -> menuDomainSupport.requestOrderLineItem(item.menuId(), item.quantity()))
                .toList();

        OrderLineItems orderLineItems = OrderLineItems.of(eatInOrderLineItems);

        tobeOrderTableService.isAvailableTable(orderTableId);

        EatInOrder eatInOrder = EatInOrder.startWaiting(orderTableId, orderLineItems);

        return orderRepository.save(eatInOrder);
    }


    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

        order.serve();

        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

        order.complete();
        return order;
    }

}
