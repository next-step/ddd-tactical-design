package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaEatInOrderRepository extends JpaRepository<EatInOrder, UUID>, EatInOrderRepository {
    @Override
    @Query(value = "" +
            "select exists( " +
            "   select 1 " +
            "   from orders o " +
            "   where o.order_table_id = :tableId " +
            "       and o.status != 'COMPLETED') ",
            nativeQuery = true)
    boolean hasOngoingOrder(@Param("tableId") UUID tableId);
}
