package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import org.springframework.test.util.ReflectionTestUtils;

public class OrderTableFixture {

  public static OrderTableName normalOrderTableName() {
    return new OrderTableName("주문테이블명");
  }

  public static OrderTableName createOrderTableName(String name) {
    return new OrderTableName(name);
  }

  public static OrderTableNumberOfGuests normalOrderTableNumberOfGuests() {
    return createOrderTableNumberOfGuests(5);
  }

  public static OrderTableNumberOfGuests createOrderTableNumberOfGuests(int numberOfGuests) {
    return new OrderTableNumberOfGuests(numberOfGuests);
  }

  public static AcceptedOrders createAcceptedOrders(List<AcceptedOrder> acceptedOrderList) {
    AcceptedOrders acceptedOrders = new AcceptedOrders();
    ReflectionTestUtils.setField(acceptedOrders, "orders", acceptedOrderList);
    return acceptedOrders;
  }

  public static AcceptedOrder createAcceptedOrder(EatInOrderStatus orderStatus) {
    AcceptedOrder acceptedOrder = new AcceptedOrder();
    ReflectionTestUtils.setField(acceptedOrder, "id", UUID.randomUUID());
    ReflectionTestUtils.setField(acceptedOrder, "status", orderStatus);
    return acceptedOrder;
  }

  public static OrderTable create(OrderTableName name, OrderTableNumberOfGuests numberOfGuests,
      boolean occupied, AcceptedOrders orders) {
    OrderTable orderTable = new OrderTable();
    ReflectionTestUtils.setField(orderTable, "id", UUID.randomUUID());
    ReflectionTestUtils.setField(orderTable, "name", name);
    ReflectionTestUtils.setField(orderTable, "numberOfGuests", numberOfGuests);
    ReflectionTestUtils.setField(orderTable, "occupied", occupied);
    ReflectionTestUtils.setField(orderTable, "orders", orders);
    return orderTable;
  }

  public static OrderTable create(boolean occupied, AcceptedOrders orders) {
    return create(normalOrderTableName(), normalOrderTableNumberOfGuests(), occupied, orders);
  }

  public static OrderTable initial(OrderTableName name) {
    return new OrderTable(name);
  }

  public static OrderTable normalInitial() {
    return new OrderTable(normalOrderTableName());
  }

}
