package kitchenpos.orders.store.infra;

import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStoreOrderRepository extends StoreOrderRepository, JpaRepository<StoreOrder, UUID> {
}
