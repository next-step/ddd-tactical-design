package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.table.application.TableService;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.application.TableGroupService;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableAlreadyGroupedException;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableNotEmptyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class GroupService {

    private final TableGroupService tableGroupService;
    private final TableService tableService;

    public GroupService(final TableGroupService tableGroupService, final TableService tableService) {
        this.tableGroupService = tableGroupService;
        this.tableService = tableService;
    }

    public TableGroup group(final List<Long> tableIds) {

        final List<Table> tables = tableService.findAllByIdIn(tableIds);

        tables.forEach(table -> {
            if (table.getTableGroupId() != null) {
                throw new TableAlreadyGroupedException("지정할 테이블이 이미 다른 단체테이블에 지정되었습니다.");
            }
            if (!table.isEmpty()) {
                throw new TableNotEmptyException("지정할 테이블이 공석이여야합니다.");
            }
        });

        final TableGroup tableGroup = tableGroupService.create(tableIds);

        tables.forEach(table -> {
            table.group(tableGroup.getId());
        });

        return tableGroup;
    }
}
