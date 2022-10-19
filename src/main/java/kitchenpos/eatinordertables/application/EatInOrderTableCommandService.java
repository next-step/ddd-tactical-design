package kitchenpos.eatinordertables.application;

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

@Transactional
@Service
public class EatInOrderTableCommandService {
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderCompletedChecker eatInOrderCompletedChecker;

    public EatInOrderTableCommandService(
        EatInOrderTableRepository eatInOrderTableRepository,
        EatInOrderCompletedChecker eatInOrderCompletedChecker
    ) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.eatInOrderCompletedChecker = eatInOrderCompletedChecker;
    }

    public EatInOrderTableResponse create(EatInOrderTableCreateRequest request) {
        EatInOrderTable eatInOrderTable = new EatInOrderTable(UUID.randomUUID(), request.getName());
        return EatInOrderTableResponse.from(eatInOrderTableRepository.save(eatInOrderTable));
    }

    public EatInOrderTableResponse sit(final UUID orderTableId) {
        EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.sit();
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    public EatInOrderTableResponse clear(final UUID orderTableId) {
        EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.clear(eatInOrderCompletedChecker);
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    public EatInOrderTableResponse changeNumberOfGuests(final UUID orderTableId, final EatInOrderTableChangeNumberOfGuestsRequest request) {
        final EatInOrderTable eatInOrderTable = findOrderTableById(orderTableId);
        eatInOrderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    private EatInOrderTable findOrderTableById(UUID orderTableId) {
        return eatInOrderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
