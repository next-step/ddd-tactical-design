package kitchenpos.eatinorders.tobe.table.application;

import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.table.domain.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TableService {

    private final TableRepository tableRepository;

    public TableService(final TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> findAllByTableGroupId(final Long tableGroupId) {
        if (tableGroupId == null || tableGroupId < 1) {
            throw new IllegalArgumentException("tableGroupId를 입력해야합니다.");
        }
        return tableRepository.findAllByTableGroupId(tableGroupId);
    }

    public List<Table> findAllByIdIn(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalArgumentException("id를 입력해야합니다.");
        }

        if (ids.size() != ids.stream()
                .distinct()
                .collect(Collectors.toList())
                .size()) {
            throw new IllegalArgumentException("중복된 id는 입력할 수 없습니다.");
        }

        final List<Table> tables = tableRepository.findAllByIdIn(ids);

        if (tables.size() != ids.size()) {
            throw new IllegalArgumentException("입력한 id가 잘못되었습니다.");
        }

        return tables;
    }
}
