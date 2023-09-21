package kitchenpos.deliveryorders.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {
}
