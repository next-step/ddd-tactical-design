package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderTableRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderTableService {

    private OrderService orderService;

    private OrderTableRepository orderTableRepository;

    public OrderTableService(OrderService orderService, OrderTableRepository orderTableRepository) {
        this.orderService = orderService;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable create(final OrderTable orderTable) {
        return orderTableRepository.save(orderTable);
    }

    public List<OrderTable> list() {
        return orderTableRepository.findAll();
    }

    @Transactional
    public OrderTable changeEmpty(final Long orderTableId, final OrderTable orderTable) {
        final OrderTable savedOrderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        final OrderStatus orderStatus = orderService.getOrderStatusByOrderTableId(orderTableId);
        if (!orderStatus.isCompleted()) {
            throw new IllegalArgumentException();
        }

        savedOrderTable.changeEmpty(orderTable);
        return savedOrderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final Long orderTableId, final OrderTable orderTable) {
        final OrderTable savedOrderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        savedOrderTable.changeNumberOfGuests(orderTable);

        return savedOrderTable;
    }

    public List<OrderTable> findAllByIdIn(List<Long> orderTableIds) {
        return orderTableRepository.findAllByIdIn(orderTableIds);
    }

    public List<OrderTable> getOrderTablesByGroupId(Long tableGroupId) {
        return orderTableRepository.findAllByTableInfoTableGroupId(tableGroupId);
    }
}
