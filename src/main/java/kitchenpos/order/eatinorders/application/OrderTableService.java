package kitchenpos.order.eatinorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.eatinorders.domain.*;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderTableCreateService orderTableCreateService;
    private final OrderTableChangeGuestService orderTableChangeGuestService;
    private final OrderTableSitService orderTableSitService;
    private final OrderTableClearService orderTableClearService;
    private final OrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository,
                             final OrderTableCreateService orderTableCreateService,
                             final OrderTableChangeGuestService orderTableChangeGuestService,
                             final OrderTableSitService orderTableSitService,
                             final OrderTableClearService orderTableClearService,
                             final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableCreateService = orderTableCreateService;
        this.orderTableChangeGuestService = orderTableChangeGuestService;
        this.orderTableSitService = orderTableSitService;
        this.orderTableClearService = orderTableClearService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTable create(final OrderTable request) {
        return this.orderTableCreateService.create(request);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        return orderTableSitService.sit(getOrderTable(orderTableId));
    }


    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        Order order = this.orderRepository.findByOrderTableId(orderTableId).orElseThrow(NotFoundOrderTableException::new);
        return this.orderTableClearService.clear(order);
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
