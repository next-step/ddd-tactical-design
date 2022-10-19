package kitchenpos.takeoutorder.infra;

import java.util.UUID;
import kitchenpos.takeoutorder.domain.TakeoutOrder;
import kitchenpos.takeoutorder.domain.TakeoutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTakeoutOrderRepository extends TakeoutOrderRepository, JpaRepository<TakeoutOrder, UUID> {
}
