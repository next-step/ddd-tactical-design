package kitchenpos.order.eatinorder.infra.repository;

import kitchenpos.order.common.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
