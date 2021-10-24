package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaOrderRepository extends TobeOrderRepository, JpaRepository<TobeOrder, UUID> {
}
