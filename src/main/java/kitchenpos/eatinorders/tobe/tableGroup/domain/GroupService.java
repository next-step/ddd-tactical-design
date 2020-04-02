package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableAlreadyGroupedException;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableNotEmptyException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupService {

    private final TableGroupRepository tableGroupRepository;

    public GroupService(final TableGroupRepository tableGroupRepository) {
        this.tableGroupRepository = tableGroupRepository;
    }

    public TableGroup group(List<Table> tables) {
        if (CollectionUtils.isEmpty(tables)) {
            throw new IllegalArgumentException("지정할 테이블을 입력해야합니다.");
        }

        if (tables.size() < 2) {
            throw new IllegalArgumentException("지정할 테이블이 2개 이상이여야합니다.");
        }

        tables.forEach(table -> {
            if (table.getTableGroupId() != null) {
                throw new TableAlreadyGroupedException("지정할 테이블이 이미 다른 단체테이블에 지정되었습니다.");
            }
            if (!table.isEmpty()) {
                throw new TableNotEmptyException("지정할 테이블이 공석이여야합니다.");
            }
        });

        final TableGroup tableGroup = tableGroupRepository.save(new TableGroup(tables.stream()
                .map(Table::getId)
                .collect(Collectors.toList())));

        tables.forEach(table -> {
            table.group(tableGroup.getId());
        });

        return tableGroup;
    }
}
