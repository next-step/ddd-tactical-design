package kitchenpos.takeoutorders.infra;

import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeoutOrderRepository extends TakeoutOrderRepository, JpaRepository<TakeoutOrder, UUID> {
}
