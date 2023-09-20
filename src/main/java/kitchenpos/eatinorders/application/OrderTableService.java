package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.exception.NotFoundOrderTableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;
    private final OrderTableCreateService orderTableCreateService;
    private final OrderTableChangeGuestService orderTableChangeGuestService;

    public OrderTableService(final OrderTableRepository orderTableRepository,
                             final OrderTableCreateService orderTableCreateService,
                             final OrderRepository orderRepository,
                             final OrderTableChangeGuestService orderTableChangeGuestService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableCreateService = orderTableCreateService;
        this.orderRepository = orderRepository;
        this.orderTableChangeGuestService = orderTableChangeGuestService;
    }

    @Transactional
    public OrderTable create(final OrderTable request) {
        return this.orderTableCreateService.create(request);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        OrderTable orderTable = getOrderTable(orderTableId).sit();
        return this.orderTableRepository.save(orderTable);
    }


    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }

        return this.orderTableRepository.save(orderTable.clear());
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        return this.orderTableChangeGuestService.changeNumberOfGuests(getOrderTable(orderTableId), request.getNumberOfGuests());
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable getOrderTable(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NotFoundOrderTableException::new);
    }
}
