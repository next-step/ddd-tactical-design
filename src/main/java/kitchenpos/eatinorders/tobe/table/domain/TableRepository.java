package kitchenpos.eatinorders.tobe.table.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findAllByTableGroupId(final Long tableGroupId);

    List<Table> findAllByIdIn(final List<Long> ids);
}
