package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.EatInOrderRepository;
import kitchenpos.eatinorder.application.port.out.EatInOrderTableRepository;
import kitchenpos.eatinorder.domain.EatInOrderStatus;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderTableService {
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderRepository orderRepository;

    public OrderTableService(final EatInOrderTableRepository eatInOrderTableRepository, final EatInOrderRepository orderRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public EatInOrderTable create(final EatInOrderTable request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final EatInOrderTable orderTable = new EatInOrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName(name);
        orderTable.setNumberOfGuests(0);
        orderTable.setOccupied(false);
        return eatInOrderTableRepository.save(orderTable);
    }

    @Transactional
    public EatInOrderTable sit(final UUID orderTableId) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrderTable.setOccupied(true);
        return eatInOrderTable;
    }

    @Transactional
    public EatInOrderTable clear(final UUID orderTableId) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByEatInOrderTableAndStatusNot(eatInOrderTable, EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        eatInOrderTable.setNumberOfGuests(0);
        eatInOrderTable.setOccupied(false);
        return eatInOrderTable;
    }

    @Transactional
    public EatInOrderTable changeNumberOfGuests(final UUID orderTableId, final EatInOrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (!eatInOrderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        eatInOrderTable.setNumberOfGuests(numberOfGuests);
        return eatInOrderTable;
    }

    @Transactional(readOnly = true)
    public List<EatInOrderTable> findAll() {
        return eatInOrderTableRepository.findAll();
    }
}
