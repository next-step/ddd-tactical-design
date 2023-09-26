package kitchenpos.order.eatinorders.application;

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

    public OrderTableService(final OrderTableRepository orderTableRepository,
                             final OrderTableCreateService orderTableCreateService,
                             final OrderTableChangeGuestService orderTableChangeGuestService,
                             final OrderTableSitService orderTableSitService,
                             final OrderTableClearService orderTableClearService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableCreateService = orderTableCreateService;
        this.orderTableChangeGuestService = orderTableChangeGuestService;
        this.orderTableSitService = orderTableSitService;
        this.orderTableClearService = orderTableClearService;
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
        return this.orderTableClearService.clear(orderTableId);
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
