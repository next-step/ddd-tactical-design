package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToBeJpaOrderTableRepository extends ToBeOrderTableRepository, JpaRepository<ToBeOrderTable, UUID> {
}
