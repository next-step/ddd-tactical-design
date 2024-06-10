package kitchenpos.eatinorders.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LegacyJpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
