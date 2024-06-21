package kitchenpos.orders.store.infra;

import java.util.UUID;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStoreOrderRepository extends StoreOrderRepository,
        JpaRepository<StoreOrder, UUID> {

}
