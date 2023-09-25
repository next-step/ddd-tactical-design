package kitchenpos.eatinorders.application.ordertable.port.in;

import java.util.UUID;
import kitchenpos.eatinorders.application.exception.NotExistOrderTableException;
import kitchenpos.support.vo.Name;

public interface OrderTableCreationUseCase {

    UUID create(final Name name);

    /**
     * @throws NotExistOrderTableException id에 해당하는 orderTable이 없을 때
     */
    void clear(final UUID id);
}
