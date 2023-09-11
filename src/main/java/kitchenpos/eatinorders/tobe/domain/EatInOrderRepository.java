package kitchenpos.eatinorders.tobe.domain;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    @Query("select eo from EatInOrder eo where eo.id = :id and eo.status = :status")
    Optional<EatInOrder> findByIdAndStatus(UUID id, EatInOrderStatus status);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, EatInOrderStatus status);
}
