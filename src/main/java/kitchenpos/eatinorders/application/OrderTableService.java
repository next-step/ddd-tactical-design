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
    private final OrderTableClearService orderTableClearService;

    private final OrderStatusService orderStatusService;

    public OrderTableService(final OrderTableRepository orderTableRepository,
                             final OrderTableCreateService orderTableCreateService,
                             final OrderRepository orderRepository,
                             final OrderTableChangeGuestService orderTableChangeGuestService, OrderTableClearService orderTableClearService, OrderStatusService orderStatusService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableCreateService = orderTableCreateService;
        this.orderRepository = orderRepository;
        this.orderTableChangeGuestService = orderTableChangeGuestService;
        this.orderTableClearService = orderTableClearService;
        this.orderStatusService = orderStatusService;
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
        return this.orderTableClearService.clear(getOrderTable(orderTableId));
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        return this.orderTableChangeGuestService.changeNumberOfGuests(getOrderTable(orderTableId), request.getNumberOfGuests());
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderTable getOrderTable(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NotFoundOrderTableException::new);
    }
}
