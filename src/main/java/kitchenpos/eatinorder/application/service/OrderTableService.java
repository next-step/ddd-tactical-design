package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.in.ClearOrderTableUseCase;
import kitchenpos.eatinorder.application.port.out.LoadEatInOrderPort;
import kitchenpos.eatinorder.application.port.out.LoadOrderTablePort;
import kitchenpos.eatinorder.application.port.out.SaveOrderTablePort;
import kitchenpos.eatinorder.application.service.model.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorder.application.service.model.CreateOrderTableRequest;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
import kitchenpos.eatinorder.domain.model.todo.OrderTable;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService implements ClearOrderTableUseCase {
    private final LoadOrderTablePort orderTableRepository;
    private final SaveOrderTablePort saveOrderTablePort;
    private final LoadEatInOrderPort loadEatInOrderPort;
    private final Profanities profanities;

    public OrderTableService(
            final LoadOrderTablePort orderTableRepository,
            final SaveOrderTablePort saveOrderTablePort,
            final LoadEatInOrderPort loadEatInOrderPort,
            final Profanities profanities
    ) {
        this.orderTableRepository = orderTableRepository;
        this.saveOrderTablePort = saveOrderTablePort;
        this.loadEatInOrderPort = loadEatInOrderPort;
        this.profanities = profanities;
    }

    @Transactional
    public OrderTable create(final CreateOrderTableRequest request) {
        OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), request.name(), profanities);
        return saveOrderTablePort.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.sit();
        return saveOrderTablePort.save(orderTable);
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        if (loadEatInOrderPort.existsByOrderTableAndStatusNot(orderTable.getId(), EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return saveOrderTablePort.save(orderTable);
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuests(request.numberOfGuests());
        return saveOrderTablePort.save(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderTable findById(UUID orderTableId) {
        if (orderTableId == null) {
            throw new IllegalArgumentException("주문 테이블 ID를 입력하세요");
        }
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }
}
