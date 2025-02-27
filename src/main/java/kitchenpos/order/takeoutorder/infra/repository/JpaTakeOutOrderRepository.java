package kitchenpos.order.takeoutorder.infra.repository;

import kitchenpos.order.takeoutorder.domain.model.TakeOutOrder;
import kitchenpos.order.takeoutorder.domain.repository.TakeOutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeOutOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {
}
