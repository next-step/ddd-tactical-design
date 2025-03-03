package kitchenpos.legacy.order.takeoutorder.infra.repository;

import kitchenpos.legacy.order.takeoutorder.domain.model.TakeOutOrder;
import kitchenpos.legacy.order.takeoutorder.domain.repository.TakeOutOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeOutOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {
}
