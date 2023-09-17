package kitchenpos.takeoutorders.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;

public interface TakeOutJpaOrderRepository extends TakeOutOrderRepository, JpaRepository<TakeOutOrder, UUID> {
}
