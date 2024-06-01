package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.todo.domain.OrderTable;
import kitchenpos.eatinorders.todo.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
