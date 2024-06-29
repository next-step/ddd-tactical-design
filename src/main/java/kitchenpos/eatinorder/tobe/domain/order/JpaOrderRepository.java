package kitchenpos.eatinorder.tobe.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
