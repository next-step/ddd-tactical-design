package kitchenpos.eatinorder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<EatInOrder, UUID> {
}
