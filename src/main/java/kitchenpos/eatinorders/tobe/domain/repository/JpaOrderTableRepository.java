package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends JpaRepository<OrderTable, UUID>, OrderTableRepository {
}
