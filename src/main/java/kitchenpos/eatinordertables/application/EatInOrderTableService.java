package kitchenpos.eatinordertables.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderTableService {
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderRepository eatInOrderRepository;

    public EatInOrderTableService(EatInOrderTableRepository eatInOrderTableRepository, EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.eatInOrderRepository = eatInOrderRepository;
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
        validateOrderCompleted(eatInOrderTable);
        eatInOrderTable.clear();
        return EatInOrderTableResponse.from(eatInOrderTable);
    }

    private void validateOrderCompleted(EatInOrderTable eatInOrderTable) {
        if (eatInOrderRepository.existsByEatInOrderTableAndStatusNot(eatInOrderTable, EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException("주문이 완료되지 않아 테이블을 정리할 수 없습니다.");
        }
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
