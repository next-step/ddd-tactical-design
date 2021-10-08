package kitchenpos.eatinorders.tobe.infra.jpa;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;
import kitchenpos.eatinorders.tobe.domain.repository.TakeOutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTakeOutOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {

}
