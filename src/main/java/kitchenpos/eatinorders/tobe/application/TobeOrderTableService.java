package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.OrderTableForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class TobeOrderTableService {
    private final TobeOrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public TobeOrderTableService(final TobeOrderTableRepository orderTableRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public TobeOrderTable create(final OrderTableForm request) {
        final String name = request.getName();
        final TobeOrderTable orderTable = new TobeOrderTable(name);
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public TobeOrderTable sit(final UUID orderTableId) {
        final TobeOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.setTheTable();
        return orderTable;
    }

//    @Transactional
//    public OrderTable clear(final UUID orderTableId) {
//        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
//            .orElseThrow(NoSuchElementException::new);
//        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//            throw new IllegalStateException();
//        }
//        orderTable.setNumberOfGuests(0);
//        orderTable.setEmpty(true);
//        return orderTable;
//    }
//
//    @Transactional
//    public OrderTable changeNumberOfGuests(final UUID orderTableId, final TobeOrderTable request) {
//        final int numberOfGuests = request.getNumberOfGuests();
//        if (numberOfGuests < 0) {
//            throw new IllegalArgumentException();
//        }
//        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
//            .orElseThrow(NoSuchElementException::new);
//        if (orderTable.isEmpty()) {
//            throw new IllegalStateException();
//        }
//        orderTable.setNumberOfGuests(numberOfGuests);
//        return orderTable;
//    }
//
//    @Transactional(readOnly = true)
//    public List<OrderTable> findAll() {
//        return orderTableRepository.findAll();
//    }
}
