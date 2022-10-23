package kitchenpos.eatinorder.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorder.domain.EatInOrderRepository;
import kitchenpos.eatinorder.domain.EatInOrderStatus;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import kitchenpos.eatinorder.domain.EatInOrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderTableService {

    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderRepository eatInOrderRepository;

    public EatInOrderTableService(final EatInOrderTableRepository eatInOrderTableRepository,
        final EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Transactional
    public EatInOrderTable create(final EatInOrderTable request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final EatInOrderTable eatInOrderTable = new EatInOrderTable();
        eatInOrderTable.setId(UUID.randomUUID());
        eatInOrderTable.setName(name);
        eatInOrderTable.setNumberOfGuests(0);
        eatInOrderTable.setOccupied(false);
        return eatInOrderTableRepository.save(eatInOrderTable);
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
        if (eatInOrderRepository.existsByOrderTableAndStatusNot(eatInOrderTable,
            EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        eatInOrderTable.setNumberOfGuests(0);
        eatInOrderTable.setOccupied(false);
        return eatInOrderTable;
    }

    @Transactional
    public EatInOrderTable changeNumberOfGuests(final UUID orderTableId,
        final EatInOrderTable request) {
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
