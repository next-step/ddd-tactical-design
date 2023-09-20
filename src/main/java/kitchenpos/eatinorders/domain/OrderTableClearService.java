package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.application.OrderStatusService;
import org.springframework.stereotype.Component;


@Component
public class OrderTableClearService {

    private final OrderTableRepository orderTableRepository;
    private final OrderStatusService orderStatusService;


    public OrderTableClearService(final OrderTableRepository orderTableRepository, final OrderStatusService orderStatusService) {
        this.orderTableRepository = orderTableRepository;
        this.orderStatusService = orderStatusService;
    }

    public OrderTable clear(final OrderTable orderTable) {
        if (orderStatusService.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        return orderTableRepository.save(orderTable.clear());
    }

}
