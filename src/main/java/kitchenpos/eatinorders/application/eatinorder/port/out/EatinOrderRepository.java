package kitchenpos.eatinorders.application.eatinorder.port.out;

import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.EatinOrder;

public interface EatinOrderRepository {

    EatinOrder save(final EatinOrder eatinOrder);

    Optional<EatinOrder> findById(final UUID id);
}
