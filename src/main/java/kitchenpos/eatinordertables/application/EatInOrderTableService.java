package kitchenpos.eatinordertables.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import kitchenpos.reader.application.EatInOrderCompletedChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderTableService {
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderCompletedChecker eatInOrderCompletedChecker;

    public EatInOrderTableService(
        EatInOrderTableRepository eatInOrderTableRepository,
        EatInOrderCompletedChecker eatInOrderCompletedChecker
    ) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.eatInOrderCompletedChecker = eatInOrderCompletedChecker;
    }

    @Transactional
    public EatInOrderTableResponse create(EatInOrderTableCreateRequest request) {
        EatInOrderTable eatInOrderTable = new EatInOrderTable(UUID.randomUUID(), request.getName());
        return EatInOrderTableResponse.from(eatInOrderTableRepository.save(eatInOrderTable));
    }

    @Transactional
    public EatInOrderTableResponse sit(final UUID orderTableId) {
        EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.sit();
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    @Transactional
    public EatInOrderTableResponse clear(final UUID orderTableId) {
        EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.clear(eatInOrderCompletedChecker);
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    @Transactional
    public EatInOrderTableResponse changeNumberOfGuests(final UUID orderTableId, final EatInOrderTableChangeNumberOfGuestsRequest request) {
        final EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderTableResponse> findAll() {
        return EatInOrderTableResponse.of(eatInOrderTableRepository.findAll());
    }

    private EatInOrderTable findOrderTableById(UUID orderTableId) {
        return eatInOrderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
