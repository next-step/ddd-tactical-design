package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepositoryImpl extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {

}
