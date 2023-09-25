package kitchenpos.eatinorders.adapter.eatinorder.out;

import java.util.UUID;
import kitchenpos.eatinorders.application.eatinorder.port.out.EatinOrderRepository;
import kitchenpos.eatinorders.domain.eatinorder.EatinOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatinOrderRepository extends EatinOrderRepository,
    JpaRepository<EatinOrder, UUID> {

}
