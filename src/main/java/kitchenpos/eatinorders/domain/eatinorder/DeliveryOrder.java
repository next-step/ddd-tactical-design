package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

import java.util.Objects;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {

  @Column(name = "delivery_address")
  private String deliveryAddress;

  protected DeliveryOrder() {

  }

  protected DeliveryOrder(OrderStatus orderStatus, OrderLineItems orderLineItems, String deliveryAddress) {
    super(OrderType.DELIVERY, orderStatus, orderLineItems);

    if (Objects.isNull(deliveryAddress)) {
      throw new IllegalArgumentException("`주문`이 생성할 때, `주문 장소`가 필수로 기입되어야 한다.");
    }

    if (deliveryAddress.isEmpty()) {
      throw new IllegalArgumentException("배달 주소는 비워 둘 수 없다.");
    }
    this.deliveryAddress = deliveryAddress;
    validate();
  }
  private void validate() {
    if (containsNegativeMenuCounts()) {
      throw new IllegalArgumentException("주문을 생성할 때, 주문상품들의 갯수를 감소시킬 수 없다.");
    }
  }

  @Override
  public void accept() {
    throw new IllegalStateException("해당 주문 타입은 라이더에게 전달되어야 합니다.");
  }

  @Override
  public void accept(KitchenridersClient kitchenridersClient) {
    if (status != OrderStatus.WAITING) {
      throw new IllegalStateException(" `주문 상태`가 `접수(ACCEPTED)`이 아닌 주문은 전달할 수 없습니다.");
    }
    status = OrderStatus.ACCEPTED;

    kitchenridersClient.requestDelivery(getId(), getOrderLineItemsSum(), deliveryAddress);
  }

  public void delivering(){
    if (status != OrderStatus.ACCEPTED) {
      throw new IllegalStateException(" `주문 상태`가 `접수(ACCEPTED)`이 아닌 주문은 배달할 수 없습니다.");
    }
    status = OrderStatus.DELIVERING;

  }

  public void delivered(){
    if (status != OrderStatus.DELIVERING) {
      throw new IllegalStateException(" `주문 상태`가 `배달중(DELIVERING)`이 아닌 주문은 배달완료할 수 없습니다.");
    }
    status = OrderStatus.DELIVERED;

  }
  @Override
  public void complete() {
    if (status != OrderStatus.DELIVERED) {
      throw new IllegalStateException(" `주문 상태`가 `배달완료(DELIVERED)`이 아닌 주문은 완료할 수 없습니다.");
    }
    status = OrderStatus.COMPLETED;
  }

}
