package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderLineItemRepository extends JpaRepository<OrderLineItem, UUID>, OrderLineItemRepository {
}
