package kitchenpos.orders.eatin.infra;

import kitchenpos.orders.eatin.domain.OrderTable;
import kitchenpos.orders.eatin.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
