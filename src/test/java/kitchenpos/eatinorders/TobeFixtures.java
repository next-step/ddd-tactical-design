package kitchenpos.eatinorders;

import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.Order;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.TableGroup;

import java.util.Arrays;
import java.util.List;

public class TobeFixtures {

    public static OrderTable nonEmptyTable() {
        return OrderTable.nonEmptyTable(1);
    }

    public static OrderTable emptyTable() {
        return OrderTable.emptyTable();
    }

    public static OrderTable groupedTable() {
        OrderTable groupedTable = emptyTable();
        groupedTable.group(1L);
        return groupedTable;
    }

    public static TableGroup table1AndTable2(List<OrderTable> orderTables) {
        return new TableGroup(orderTables);
    }

    public static Order orderForTable1() {
        return new Order(1L, Arrays.asList(orderLineItem()));
    }

    public static Order orderForEmptyTable() {
        return new Order(2L, Arrays.asList(orderLineItem()));
    }

    public static OrderLineItem orderLineItem() {
        return new OrderLineItem(1L, 2);
    }
}
