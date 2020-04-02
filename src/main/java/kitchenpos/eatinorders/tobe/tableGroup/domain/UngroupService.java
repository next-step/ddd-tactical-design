package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.order.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.OrderNotCompletedException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UngroupService {

    public void ungroup(final TableGroup tableGroup, final List<Table> tables, final List<Order> orders) {
        if (CollectionUtils.isEmpty(tables)) {
            throw new IllegalArgumentException("지정 해제할 테이블을 입력해야합니다.");
        }

        tables.forEach(table -> {
            if (table.getTableGroupId() != tableGroup.getId()) {
                throw new IllegalArgumentException("지정 해제할 테이블이 잘못 입력되었습니다.");
            }
        });

        final List<Long> tableIds = tables.stream()
                .map(Table::getId)
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(order -> {
                if (!tableIds.contains(order.getTableId())) {
                    throw new IllegalArgumentException("주문이 잘못 입력되었습니다.");
                }

                if (OrderStatus.COMPLETION != order.getStatus()) {
                    throw new OrderNotCompletedException("주문이 완료되지 않았습니다.");
                }
            });
        }

        tables.forEach(Table::ungroup);
    }
}
