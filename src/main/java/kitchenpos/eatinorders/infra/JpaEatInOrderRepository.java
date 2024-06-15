package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository
    extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {}
