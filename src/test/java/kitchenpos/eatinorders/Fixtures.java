package kitchenpos.eatinorders;

import kitchenpos.eatinorders.model.*;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Fixtures {
    public static final Long ORDER_FOR_TABLE1_ID = 1L;
    public static final Long TABLE1_ID = 1L;
    public static final Long TABLE2_ID = 2L;
    public static final Long TABLE1_AND_TABLE2_ID = 1L;
    public static final Long TWO_FRIED_CHICKENS_ID = 1L;

    public static Order orderForTable1() {
        final Order order = new Order();
        order.setId(ORDER_FOR_TABLE1_ID);
        order.setOrderTableId(TABLE1_ID);
        order.setOrderStatus(OrderStatus.COOKING.name());
        order.setOrderedTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        return order;
    }

    public static OrderTable table1() {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(TABLE1_ID);
        orderTable.setTableGroupId(null);
        orderTable.setNumberOfGuests(0);
        orderTable.setEmpty(false);
        return orderTable;
    }

    public static OrderTable table2() {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(TABLE2_ID);
        orderTable.setTableGroupId(null);
        orderTable.setNumberOfGuests(0);
        orderTable.setEmpty(false);
        return orderTable;
    }

    public static OrderTable emptyTable1() {
        final OrderTable orderTable = table1();
        orderTable.setEmpty(true);
        return orderTable;
    }

    public static OrderTable emptyTable2() {
        final OrderTable orderTable = table2();
        orderTable.setEmpty(true);
        return orderTable;
    }

    public static OrderTable groupedTable1() {
        final OrderTable orderTable = table1();
        orderTable.setTableGroupId(TABLE1_AND_TABLE2_ID);
        return orderTable;
    }

    public static OrderTable groupedTable2() {
        final OrderTable orderTable = table2();
        orderTable.setTableGroupId(TABLE1_AND_TABLE2_ID);
        return orderTable;
    }

    public static TableGroup table1AndTable2() {
        final TableGroup tableGroup = new TableGroup();
        tableGroup.setId(TABLE1_AND_TABLE2_ID);
        tableGroup.setCreatedDate(LocalDateTime.of(2020, 1, 1, 12, 0));
        tableGroup.setOrderTables(Arrays.asList(groupedTable1(), groupedTable2()));
        return tableGroup;
    }

    private static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setOrderId(ORDER_FOR_TABLE1_ID);
        orderLineItem.setMenuId(TWO_FRIED_CHICKENS_ID);
        orderLineItem.setQuantity(2);
        return orderLineItem;
    }
}
