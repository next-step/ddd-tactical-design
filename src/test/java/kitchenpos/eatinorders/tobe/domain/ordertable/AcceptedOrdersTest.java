package kitchenpos.eatinorders.tobe.domain.ordertable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AcceptedOrdersTest {

  @DisplayName("완료되지 않은 주문이 존재한다.")
  @Test
  public void existsIncompleteOrder() {
    AcceptedOrder acceptedOrder1 = OrderTableFixture.createAcceptedOrder(
        EatInOrderStatus.COMPLETED);
    AcceptedOrder acceptedOrder2 = OrderTableFixture.createAcceptedOrder(EatInOrderStatus.WAITING);
    AcceptedOrders acceptedOrders = OrderTableFixture.createAcceptedOrders(
        List.of(acceptedOrder1, acceptedOrder2));
    boolean existsIncompleteOrder = acceptedOrders.existsIncompleteOrder();
    assertThat(existsIncompleteOrder).isTrue();
  }
}
