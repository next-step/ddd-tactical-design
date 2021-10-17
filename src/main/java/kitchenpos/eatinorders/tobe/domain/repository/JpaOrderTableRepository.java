package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tobeJpaOrderTableRepository")
public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
