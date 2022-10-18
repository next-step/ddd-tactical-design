package kitchenpos.eatinordertables.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.reader.application.EatInOrderTableOccupiedChecker;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderTableOccupiedCheckerImpl implements EatInOrderTableOccupiedChecker {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public EatInOrderTableOccupiedCheckerImpl(EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Override
    public boolean isEatInOrderTableNotOccupied(UUID eatInOrderTableId) {
        EatInOrderTable eatInOrderTable = findEatInOrderTableById(eatInOrderTableId);
        return eatInOrderTable.isNotOccupied();
    }

    private EatInOrderTable findEatInOrderTableById(UUID eatInOrderTableId) {
        return eatInOrderTableRepository.findById(eatInOrderTableId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 주문 테이블이 없습니다."));
    }
}
