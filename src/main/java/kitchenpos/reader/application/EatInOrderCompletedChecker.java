package kitchenpos.reader.application;

import java.util.UUID;

public interface EatInOrderCompletedChecker {

    boolean existsNotCompletedEatInOrder(UUID eatInOrderTable);
}
