package kitchenpos.eatinorders.tobe.tableGroup.application;

import kitchenpos.eatinorders.tobe.tableGroup.domain.TableGroup;
import kitchenpos.eatinorders.tobe.tableGroup.domain.TableGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableGroupService {

    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final TableGroupRepository tableGroupRepository) {
        this.tableGroupRepository = tableGroupRepository;
    }

    public TableGroup create(List<Long> tableIds) {
        final TableGroup tableGroup = new TableGroup(tableIds);
        return tableGroupRepository.save(tableGroup);
    }
}
