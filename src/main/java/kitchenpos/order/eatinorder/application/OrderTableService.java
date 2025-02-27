package kitchenpos.order.eatinorder.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.eatinorder.application.dto.CreateOrderTableServiceRq;
import kitchenpos.order.eatinorder.application.dto.OrderTableServiceRs;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.model.OrderTableName;
import kitchenpos.order.eatinorder.domain.model.ReleaseOrderTableEvent;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorder.domain.service.OrderTableOccupationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderTableOccupationManager orderTableOccupationManager;

    public OrderTableService(final OrderTableRepository orderTableRepository,
                             final OrderTableOccupationManager orderTableOccupationManager) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableOccupationManager = orderTableOccupationManager;
    }

    @Transactional
    public OrderTableServiceRs create(final CreateOrderTableServiceRq request) {
        final String name = request.getName();
        final OrderTable orderTable = orderTableRepository.save(new OrderTable(new OrderTableName(name)));
        return new OrderTableServiceRs(orderTable);
    }

    @Transactional
    public OrderTableServiceRs sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.occupyTable();
        return new OrderTableServiceRs(orderTable);
    }

    @Transactional
    public OrderTableServiceRs clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTableOccupationManager.release(new ReleaseOrderTableEvent(orderTable));
        return new OrderTableServiceRs(orderTable);
    }

    @Transactional
    public OrderTableServiceRs changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return new OrderTableServiceRs(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableServiceRs> findAll() {
        return orderTableRepository.findAll().stream()
                .map(OrderTableServiceRs::new)
                .toList();
    }
}
