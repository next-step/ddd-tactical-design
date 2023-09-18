package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ToBeOrderTableService {
    private final ToBeOrderTableRepository orderTableRepository;
    private final ToBeOrderRepository orderRepository;

    public ToBeOrderTableService(final ToBeOrderTableRepository orderTableRepository, final ToBeOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public ToBeOrderTable create(final ToBeOrderTable request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final ToBeOrderTable orderTable = new ToBeOrderTable(new OrderTableName(name),new NumberOfGuests(0),false);
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public ToBeOrderTable sit(final UUID orderTableId) {
        final ToBeOrderTable orderTable = getOrderTableById(orderTableId);
        orderTable.changeOccupied(true);
        return orderTable;
    }

    @Transactional
    public ToBeOrderTable clear(final UUID orderTableId) {
        final ToBeOrderTable orderTable = getOrderTableById(orderTableId);

        tableClear(orderTable);

        orderTable.zeroizeNumberOfGuests(new NumberOfGuests(0));
        orderTable.changeOccupied(false);
        return orderTable;
    }

    @Transactional
    public ToBeOrderTable changeNumberOfGuests(final UUID orderTableId, final ToBeOrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final ToBeOrderTable orderTable = getOrderTableById(orderTableId);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTable.zeroizeNumberOfGuests(new NumberOfGuests(numberOfGuests));
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<ToBeOrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private ToBeOrderTable getOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
    }

    private void tableClear(ToBeOrderTable orderTable) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, ToBeOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
    }



}
