package kitchenpos.eatinorders.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("TobeJpaOrderRepository")
public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
    @Override
    @Query("SELECT case when count(o)> 0 then true else false end from Order o where (o.orderTable.id) = (:orderTableId) AND (o.status) <> 'COMPLETED'")
    boolean existsByOrderTableIdAndNotCompleted(@Param("orderTableId") UUID orderTableId);
}
