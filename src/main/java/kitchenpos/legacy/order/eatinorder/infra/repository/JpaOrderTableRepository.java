package kitchenpos.legacy.order.eatinorder.infra.repository;

import kitchenpos.legacy.order.common.model.OrderTable;
import kitchenpos.legacy.order.eatinorder.domain.repository.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
