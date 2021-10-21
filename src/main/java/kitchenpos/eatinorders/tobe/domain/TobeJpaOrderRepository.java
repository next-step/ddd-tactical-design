package kitchenpos.eatinorders.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaOrderRepository extends TobeOrderRepository, JpaRepository<TobeOrder, UUID> {
}
