package kitchenpos.eatinorder.application.port.in;

import java.util.UUID;

public interface ClearOrderTableUseCase {
    boolean clearWhenAllOrdersCompleted(final UUID orderTableId);
}
