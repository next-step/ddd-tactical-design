package kitchenpos.takeoutorders.infra;

import java.util.UUID;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTakeoutOrderRepository
    extends TakeoutOrderRepository, JpaRepository<TakeoutOrder, UUID> {}
