package kitchenpos.eatinorders.tobe.domain;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    @Query("select ot from OrderTable ot where ot.id = :id and ot.occupied = false")
    Optional<OrderTable> findByIdNotOccupied(UUID id);

    @Query("select ot from OrderTable ot where ot.id = :id and ot.occupied = true")
    Optional<OrderTable> findByIdOccupied(UUID id);

    List<OrderTable> findAll();
}

