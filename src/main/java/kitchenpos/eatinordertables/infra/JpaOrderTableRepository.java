package kitchenpos.eatinordertables.infra;

import kitchenpos.eatinordertables.domain.OrderTable;
import kitchenpos.eatinordertables.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("TobeJpaOrderTableRepository")
public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
