package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.tobe.OrderTable;
import kitchenpos.eatinorders.domain.tobe.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
