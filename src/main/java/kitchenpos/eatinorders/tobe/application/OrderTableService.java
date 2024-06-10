package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.OrderStatusService;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final OrderStatusService orderStatusService;

    public OrderTableService(OrderTableRepository orderTableRepository, OrderStatusService orderStatusService) {
        this.orderTableRepository = orderTableRepository;
        this.orderStatusService = orderStatusService;
    }

    @Transactional
    public void create(final String name) {
        OrderTable orderTable = new OrderTable(name);
        orderTableRepository.save(orderTable);
    }

    @Transactional
    public void sit(final UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        orderTable.sit();
    }

    @Transactional
    public void clear(final UUID orderTableId) {
        orderStatusService.isCompletedInOrderTable(orderTableId);
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);
        orderTable.clear();
    }


}
