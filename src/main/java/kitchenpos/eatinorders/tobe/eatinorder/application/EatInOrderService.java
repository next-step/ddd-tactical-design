package kitchenpos.eatinorders.tobe.eatinorder.application;

import kitchenpos.eatinorders.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.eatinorder.domain.MenuLoader;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.CreateRequest;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.MenuResponse;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.tobe.ordertable.application.OrderTableService;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final OrderTableService orderTableService;
    private final MenuLoader menuLoader;
    private final MenuClient menuClient;

    public EatInOrderService(final OrderRepository orderRepository, final OrderTableService orderTableService, final MenuLoader menuLoader, final MenuClient menuClient) {
        this.orderRepository = orderRepository;
        this.orderTableService = orderTableService;
        this.menuLoader = menuLoader;
        this.menuClient = menuClient;
    }

    @Transactional
    public EatInOrder create(final CreateRequest request) {
        final List<OrderLineItem> orderLineItems = loadOrderLineItems(request.getOrderLineItemRequests());
        final OrderTable orderTable = loadOrderTable(request.getOrderTableId());
        final EatInOrder order = new EatInOrder(orderLineItems, orderTable);
        return orderRepository.save(order);
    }

    private List<OrderLineItem> loadOrderLineItems(final List<OrderLineItemCreateRequest> requests) {
        return menuLoader.loadOrderLineItems(requests,
                menuClient.findAllByIdn(
                        requests.stream()
                                .map(OrderLineItemCreateRequest::getMenuId)
                                .collect(toList())
                ));
    }

    private OrderTable loadOrderTable(final UUID orderTableId) {
        final OrderTable orderTable = orderTableService.findById(orderTableId);
        verifyOrderTable(orderTable);
        return orderTable;
    }

    private void verifyOrderTable(final OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("주문 테이블은 비어있을 수 없습니다.");
        }
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = findById(orderId);
        order.accept();
        return order;
    }

//
//    @Transactional
//    public EatInOrder serve(final UUID orderId) {
//        final EatInOrder order = orderRepository.findById(orderId)
//            .orElseThrow(NoSuchElementException::new);
//        if (order.getStatus() != OrderStatus.ACCEPTED) {
//            throw new IllegalStateException();
//        }
//        order.setStatus(OrderStatus.SERVED);
//        return order;
//    }
//

//    @Transactional
//    public EatInOrder complete(final UUID orderId) {
//        final EatInOrder order = orderRepository.findById(orderId)
//            .orElseThrow(NoSuchElementException::new);
//        final OrderType type = order.getType();
//        final OrderStatus status = order.getStatus();
//        if (type == OrderType.DELIVERY) {
//            if (status != OrderStatus.DELIVERED) {
//                throw new IllegalStateException();
//            }
//        }
//        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
//            if (status != OrderStatus.SERVED) {
//                throw new IllegalStateException();
//            }
//        }
//        order.setStatus(OrderStatus.COMPLETED);
//        if (type == OrderType.EAT_IN) {
//            final OrderTable orderTable = order.getOrderTable();
//            if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//                orderTable.setNumberOfGuests(0);
//                orderTable.setEmpty(true);
//            }
//        }
//        return order;
//    }
//
    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EatInOrder findById(final UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}
