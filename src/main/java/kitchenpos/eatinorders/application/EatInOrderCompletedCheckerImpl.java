package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.domain.EatInOrderStatus.COMPLETED;

import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.reader.application.EatInOrderCompletedChecker;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderCompletedCheckerImpl implements EatInOrderCompletedChecker {

    private final EatInOrderRepository eatInOrderRepository;

    public EatInOrderCompletedCheckerImpl(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Override
    public boolean existsNotCompletedEatInOrder(UUID eatInOrderTableId) {
        return eatInOrderRepository.existsByEatInOrderTableIdAndStatusNot(
            eatInOrderTableId,
            COMPLETED
        );
    }
}
