package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, EatInOrderId> {
    @Query("select o from EatInOrder o where o.orderTableId = :orderTableId and o.status = :status")
    @Override
    boolean existsByOrderTableAndStatusNot(@Param("orderTableId") OrderTableId orderTableId, @Param("status") EatInOrderStatus status);
}
