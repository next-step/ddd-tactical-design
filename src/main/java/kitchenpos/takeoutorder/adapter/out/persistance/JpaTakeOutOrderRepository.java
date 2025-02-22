package kitchenpos.takeoutorder.adapter.out.persistance;

import kitchenpos.takeoutorder.application.port.out.TakeOutOrderRepository;
import kitchenpos.takeoutorder.domain.TakeOutOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeOutOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {
}
