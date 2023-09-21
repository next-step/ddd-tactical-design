package kitchenpos.eatinorders.tobe.domain.infra;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, UUID>, OrderRepository {

}
