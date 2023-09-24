package kitchenpos.takeoutorders.tobe.infra;

import kitchenpos.takeoutorders.tobe.domain.TakeOutOrder;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeOutOrderRepository extends JpaRepository<TakeOutOrder, UUID>, TakeOutOrderRepository {
}
