package kitchenpos.eatinorders.tobe.domain.eatinorder.repository;

import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findAllByTableInfoTableGroupId(Long tableGroupId);

    List<OrderTable> findAllByIdIn(List<Long> orderTableIds);
}
