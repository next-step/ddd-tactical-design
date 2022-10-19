package kitchenpos.reader.application;

import java.util.UUID;

public interface EatInOrderTableOccupiedChecker {

    boolean isEatInOrderTableNotOccupied(UUID eatInOrderTableId);
}
