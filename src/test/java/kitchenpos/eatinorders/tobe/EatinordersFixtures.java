package kitchenpos.eatinorders.tobe;

import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.order.domain.OrderLine;
import kitchenpos.eatinorders.tobe.order.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.domain.TableGroup;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

public class EatinordersFixtures {
    public static final Long TABLEGROUP_1_ID = 1L;

    public static final Long TABLE_EMPTY_1_ID = 1L;
    public static final Long TABLE_EMPTY_2_ID = 2L;
    public static final Long TABLE_NOT_EMPTY_ID = 3L;
    public static final Long TABLE_GROUPED_1_ID = 4L;
    public static final Long TABLE_GROUPED_2_ID = 5L;

//    public static final Long ORDERLINE_TWO_FRIED_CHICKENS = 1L;
//    public static final Long ORDERLINE_ONE_FRIED_CHICKENS = 2L;

    public static final Long ORDER_COOKING = 1L;
    public static final Long ORDER_COMPLETED = 2L;

    public static TableGroup tableGroup1() {
        final TableGroup tableGroup = new TableGroup(Arrays.asList(TABLE_GROUPED_1_ID, TABLE_GROUPED_2_ID));
        ReflectionTestUtils.setField(tableGroup, "id", TABLEGROUP_1_ID);
        ReflectionTestUtils.setField(tableGroup, "createdDate", LocalDateTime.now());
        return tableGroup;
    }

    public static Table tableEmpty1() {
        final Table table = new Table(1, true);
        ReflectionTestUtils.setField(table, "id", TABLE_EMPTY_1_ID);
        return table;
    }

    public static Table tableEmpty2() {
        final Table table = new Table(2, true);
        ReflectionTestUtils.setField(table, "id", TABLE_EMPTY_2_ID);
        return table;
    }

    public static Table tableNotEmpty() {
        final Table table = new Table(3, false);
        ReflectionTestUtils.setField(table, "id", TABLE_NOT_EMPTY_ID);
        return table;
    }

    public static Table tableGrouped1() {
        final Table table = new Table(4, true);
        ReflectionTestUtils.setField(table, "id", TABLE_GROUPED_1_ID);
        table.group(TABLEGROUP_1_ID);
        return table;
    }

    public static Table tableGrouped2() {
        final Table table = new Table(4, true);
        ReflectionTestUtils.setField(table, "id", TABLE_GROUPED_2_ID);
        table.group(TABLEGROUP_1_ID);
        return table;
    }

    public static OrderLine orderLineTwoFriedChickens() {
        final OrderLine orderLine = new OrderLine(1L, 1);
        return orderLine;
    }

    public static OrderLine orderLineOneFriedChicken() {
        final OrderLine orderLine = new OrderLine(2L, 1);
        return orderLine;
    }

    public static Order orderCooking() {
        final Order order = new Order(TABLE_NOT_EMPTY_ID, Arrays.asList(orderLineOneFriedChicken()));
        ReflectionTestUtils.setField(order, "id", ORDER_COOKING);
        ReflectionTestUtils.setField(order, "orderedTime", LocalDateTime.now());
        return order;
    }

    public static Order orderCompleted() {
        final Order order = new Order(TABLE_NOT_EMPTY_ID, Arrays.asList(orderLineOneFriedChicken()));
        ReflectionTestUtils.setField(order, "id", ORDER_COMPLETED);
        ReflectionTestUtils.setField(order, "orderedTime", LocalDateTime.now());
        ReflectionTestUtils.setField(order, "status", OrderStatus.COMPLETION);
        return order;
    }

    public static Order orderCompletedFromTableGrouped1() {
        final Order order = new Order(TABLE_GROUPED_1_ID, Arrays.asList(orderLineOneFriedChicken()));
        ReflectionTestUtils.setField(order, "id", ORDER_COMPLETED);
        ReflectionTestUtils.setField(order, "orderedTime", LocalDateTime.now());
        ReflectionTestUtils.setField(order, "status", OrderStatus.COMPLETION);
        return order;
    }

    public static Order orderCookingFromTableGrouped1() {
        final Order order = new Order(TABLE_GROUPED_1_ID, Arrays.asList(orderLineOneFriedChicken()));
        ReflectionTestUtils.setField(order, "id", ORDER_COOKING);
        ReflectionTestUtils.setField(order, "orderedTime", LocalDateTime.now());
        ReflectionTestUtils.setField(order, "status", OrderStatus.COOKING);
        return order;
    }

    public static Order orderCompletedFromTableGrouped2() {
        final Order order = new Order(TABLE_GROUPED_2_ID, Arrays.asList(orderLineTwoFriedChickens()));
        ReflectionTestUtils.setField(order, "id", ORDER_COMPLETED);
        ReflectionTestUtils.setField(order, "orderedTime", LocalDateTime.now());
        ReflectionTestUtils.setField(order, "status", OrderStatus.COMPLETION);
        return order;
    }
}
