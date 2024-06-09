package kitchenpos.eatinorders.tobe.repository;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
