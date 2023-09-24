package kitchenpos.eatinorders.application.ordertable.port.in;

import java.util.UUID;
import kitchenpos.support.vo.Name;

public interface OrderTableCreationUseCase {

    UUID create(final Name name);
}
