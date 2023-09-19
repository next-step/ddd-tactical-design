package kitchenpos.order.takeoutorder.infra;

import java.util.UUID;
import kitchenpos.order.takeoutorder.domain.TakeOutOrder;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTakeoutOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {

}
