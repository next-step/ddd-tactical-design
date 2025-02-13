package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.domain.model.OrderTable;
import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
