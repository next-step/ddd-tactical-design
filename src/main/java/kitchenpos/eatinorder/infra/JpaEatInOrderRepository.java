package kitchenpos.eatinorder.infra;

import java.util.UUID;
import kitchenpos.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorder.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderRepository extends EatInOrderRepository,
    JpaRepository<EatInOrder, UUID> {

}
