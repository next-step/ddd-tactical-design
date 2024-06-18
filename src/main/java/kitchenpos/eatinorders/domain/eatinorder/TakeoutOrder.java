package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeoutOrder extends Order {

  protected TakeoutOrder() {
    super();
  }

  protected TakeoutOrder(OrderStatus orderStatus, OrderLineItems orderLineItems) {
    super(OrderType.TAKEOUT, orderStatus, orderLineItems);
    validate();
  }

  private void validate() {
    if (containsNegativeMenuCounts()) {
      throw new IllegalArgumentException("주문을 생성할 때, 주문상품들의 갯수를 감소시킬 수 없다.");
    }
  }

  @Override
  public void accept(PassToRiderService passToRiderService) {
    if (status != OrderStatus.WAITING) {
      throw new IllegalStateException(" `주문 상태`가 `대기중(WAITING)`이 아닌 주문은 수락할 수 없습니다.");
    }
    status = OrderStatus.ACCEPTED;
  }

  @Override
  public void delivering() {
    throw new IllegalStateException("해당 주문 타입은 라이더에게 전달되지 않아도 됩니다.");
  }

  @Override
  public void delivered() {
    throw new IllegalStateException("해당 주문 타입은 라이더에게 전달되지 않아도 됩니다.");
  }

  @Override
  public void complete(ClearOrderTableService clearOrderTableService) {
    if (status != OrderStatus.SERVED) {
      throw new IllegalStateException(" `주문 상태`가 `전달(SERVED)`이 아닌 주문은 주문완료할 수 없습니다.");
    }
    status = OrderStatus.COMPLETED;
  }
}
