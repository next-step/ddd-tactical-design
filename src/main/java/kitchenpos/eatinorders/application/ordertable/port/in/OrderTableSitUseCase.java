package kitchenpos.eatinorders.application.ordertable.port.in;

import java.util.UUID;
import kitchenpos.eatinorders.application.exception.NotExistOrderTableException;

public interface OrderTableSitUseCase {

    /**
     * @throws NotExistOrderTableException id에 해당하는 orderTable이 없을 때
     * @throws IllegalArgumentException    numberOfGuests가 음수일 때
     * @throws IllegalStateException       orderTable이 점유된 상태가 아닐 때
     */
    void sit(final UUID id, final int numberOfGuests);

    /**
     * @throws NotExistOrderTableException id에 해당하는 orderTable이 없을 때
     * @throws IllegalArgumentException    numberOfGuests가 음수일 때
     * @throws IllegalStateException       orderTable이 점유된 상태가 아닐 때
     */
    void changeGuests(final UUID id, final int numberOfGuests);
}
