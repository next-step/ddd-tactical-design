package kitchenpos.eatinorder.tobe.domain;

import kitchenpos.eatinorder.tobe.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
