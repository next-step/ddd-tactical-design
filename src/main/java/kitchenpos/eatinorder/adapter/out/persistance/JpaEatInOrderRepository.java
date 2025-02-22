package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.application.port.out.EatInOrderRepository;
import kitchenpos.eatinorder.domain.EatInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
