package kitchenpos.order.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
