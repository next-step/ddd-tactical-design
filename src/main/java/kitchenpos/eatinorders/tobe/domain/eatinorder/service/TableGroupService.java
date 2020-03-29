package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.TableGroup;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.TableGroupRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableGroupService {

    private final OrderService orderService;

    private final OrderTableService orderTableService;

    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(OrderService orderService, OrderTableService orderTableService, TableGroupRepository tableGroupRepository) {
        this.orderService = orderService;
        this.orderTableService = orderTableService;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroup create(final TableGroup tableGroup) {
        final List<OrderTable> savedOrderTables = orderTableService.findAllByIdIn(tableGroup.getOrderTableIds());

        if (tableGroup.getOrderTables().size() != savedOrderTables.size()) {
            throw new IllegalArgumentException();
        }

        final TableGroup savedTableGroup = tableGroupRepository.save(new TableGroup(savedOrderTables));
        final Long tableGroupId = savedTableGroup.getId();

        savedTableGroup.updateOrderTables(savedOrderTables.stream()
                .map(orderTable -> {
                    orderTable.group(tableGroupId);
                    return orderTableService.create(orderTable);
                })
                .collect(Collectors.toList()));

        return savedTableGroup;
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableService.getOrderTablesByGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        if (orderService.existsByOrderTableIdInAndOrderStatus(orderTableIds)) {
            throw new IllegalArgumentException();
        }

        for (final OrderTable orderTable : orderTables) {
            orderTable.ungroup();
            orderTableService.create(orderTable);
        }
    }
}
