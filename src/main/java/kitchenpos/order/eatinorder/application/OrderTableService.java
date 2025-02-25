package kitchenpos.order.eatinorder.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.model.OrderTableName;
import kitchenpos.order.eatinorder.domain.model.ReleaseOrderTableEvent;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorder.domain.service.OrderTableOccupationManager;
import kitchenpos.order.eatinorder.service.dto.CreateOrderTableServiceRq;
import kitchenpos.order.eatinorder.service.dto.CreateOrderTableServiceRs;
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
    public CreateOrderTableServiceRs create(final CreateOrderTableServiceRq request) {
        final String name = request.getName();
        final OrderTable orderTable = orderTableRepository.save(new OrderTable(new OrderTableName(name)));
        return new CreateOrderTableServiceRs(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.occupyTable();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTableOccupationManager.release(new ReleaseOrderTableEvent(orderTable));
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
