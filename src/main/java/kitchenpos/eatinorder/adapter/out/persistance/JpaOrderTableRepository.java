package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.adapter.out.persistance.entity.OrderTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends JpaRepository<OrderTableEntity, UUID> {
}
