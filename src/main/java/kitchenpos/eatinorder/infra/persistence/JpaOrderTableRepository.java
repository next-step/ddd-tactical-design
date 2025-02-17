package kitchenpos.eatinorder.infra.persistence;

import kitchenpos.eatinorder.domain.OrderTable;
import kitchenpos.eatinorder.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
