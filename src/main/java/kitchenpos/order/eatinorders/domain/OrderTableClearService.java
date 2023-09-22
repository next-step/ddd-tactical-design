package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.application.OrderStatusService;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;


@Component
public class OrderTableClearService {

    private final OrderTableRepository orderTableRepository;
    private final OrderStatusService orderStatusService;


    public OrderTableClearService(final OrderTableRepository orderTableRepository, final OrderStatusService orderStatusService) {
        this.orderTableRepository = orderTableRepository;
        this.orderStatusService = orderStatusService;
    }

    public OrderTable clear(final Order order) {
        OrderTable orderTable = order.getOrderTable();
        if (!orderStatusService.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTableRepository.save(orderTable.clear());
        }
        return orderTable;
    }

    public OrderTable clear(final OrderTable orderTable) {
        if (!orderStatusService.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTableRepository.save(orderTable.clear());
        }
        return orderTable;
    }

}
