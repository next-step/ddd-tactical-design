package kitchenpos.eatinorders.tobe.infra.persistence.eatinorder;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.eatinorder.TobeEatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TobeJpaEatInOrderRepository extends TobeEatInOrderRepository,
    JpaRepository<EatInOrder, UUID> {

}
