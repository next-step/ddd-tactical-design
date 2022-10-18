package kitchenpos.eatinordertables.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.event.EatInOrderCompletedEvent;
import kitchenpos.reader.application.EatInOrderCompletedChecker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EatInOrderTableEventHandler {

    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final EatInOrderCompletedChecker eatInOrderCompletedChecker;

    public EatInOrderTableEventHandler(
        EatInOrderTableRepository eatInOrderTableRepository,
        EatInOrderCompletedChecker eatInOrderCompletedChecker
    ) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.eatInOrderCompletedChecker = eatInOrderCompletedChecker;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAndCheckPrice(EatInOrderCompletedEvent event) {
        EatInOrderTable eatInOrderTable = findEatInOrderTableById(event.getEatInOrderTableId());
        eatInOrderTable.clear(eatInOrderCompletedChecker);
    }

    private EatInOrderTable findEatInOrderTableById(UUID eatInOrderTableId) {
        return eatInOrderTableRepository.findById(eatInOrderTableId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 주문 테이블을 찾을 수 없습니다."));
    }
}
