package kitchenpos.takeoutorder.adapter.out.persistance;

import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import kitchenpos.eatinorder.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTakeOutOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
