package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.order.application.OrderService;
import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.order.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.table.application.TableService;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.OrderNotCompletedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UngroupService {

    private final TableService tableService;
    private final OrderService orderService;

    public UngroupService(final TableService tableService, final OrderService orderService) {
        this.tableService = tableService;
        this.orderService = orderService;
    }

    public void ungroup(final Long tableGroupId) {

        final List<Table> tables = tableService.findAllByTableGroupId(tableGroupId);

        final List<Order> orders = orderService.findAllByTableIdIn(tables.stream()
                .map(Table::getId)
                .collect(Collectors.toList()));

        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(order -> {
                if (OrderStatus.COMPLETION != order.getStatus()) {
                    throw new OrderNotCompletedException("주문이 완료되지 않았습니다.");
                }
            });
        }

        tables.forEach(Table::ungroup);
    }
}
