package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.TableName;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.OrderTableForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeOrderTableService {
    private final TobeOrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public TobeOrderTableService(final TobeOrderTableRepository orderTableRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableForm create(final OrderTableForm request) {
        final TableName tableName = new TableName(request.getName());
        final TobeOrderTable orderTable = new TobeOrderTable(tableName);
        return OrderTableForm.of(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableForm sit(final UUID orderTableId) {
        final TobeOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.setTheTable();
        return OrderTableForm.of(orderTable);
    }

    @Transactional
    public OrderTableForm clear(final UUID orderTableId) {
        final TobeOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        // TODO : OrderService 진행할 때 리팩토링 예정
//        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//            throw new IllegalStateException();
//        }
        orderTable.clear();
        return OrderTableForm.of(orderTable);
    }

    @Transactional
    public OrderTableForm changeNumberOfGuests(final UUID orderTableId, final OrderTableForm request) {
        final int numberOfGuests = request.getNumberOfGuests();
        final TobeOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return OrderTableForm.of(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableForm> findAll() {
        return orderTableRepository.findAll().stream()
                .map(OrderTableForm::of)
                .collect(Collectors.toList());
    }
}
