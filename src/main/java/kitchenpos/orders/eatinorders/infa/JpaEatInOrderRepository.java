package kitchenpos.orders.eatinorders.infa;

import kitchenpos.orders.eatinorders.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, EatInOrderId> {
    @Query("select count(o) < 0 from EatInOrder o where o.orderTableId = :orderTableId and o.status = :status")
    @Override
    boolean existsByOrderTableAndStatusNot(@Param("orderTableId") OrderTableId orderTableId, @Param("status") EatInOrderStatus status);
}
