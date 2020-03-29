package kitchenpos.eatinorders.tobe.domain.eatinorder.repository;

import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.TableGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableGroupRepository extends JpaRepository<TableGroup, Long> {
}
