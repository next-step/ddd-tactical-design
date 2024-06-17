package kitchenpos.order.takeout.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTakeOutOrderRepository extends TakeOutOrderRepository,
    JpaRepository<TakeOutOrder, UUID> {

}
