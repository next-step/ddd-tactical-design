package kitchenpos.ordertable.tobe.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.ordertable.tobe.domain.service.OrderTableRelatedOrderStatusCheckService;

@Entity
@Table(name = "order_table")
public class OrderTable {

  private static final String INCOMPLETED_ORDER_EXIST = "아직 완료되지 않은 주문이 있어서 테이블을 비어있음으로 설정할 수 없습니다.";
  private static final String EMPTY_TABLE_CAN_NOT_CHANGE_NUMBER_OF_GUESTS = "손님이 사용중인 테이블만 손님 수를 변경할 수 있습니다.";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private OrderTableName name;

  @Embedded
  private NumberOfGuests numberOfGuests;

  private boolean empty;

  protected OrderTable() {
  }

  public OrderTable(String name, int numberOfGuests, boolean empty) {
    this.name = new OrderTableName(name);
    this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    this.empty = empty;
  }

  public OrderTable toBeUsed() {
    this.empty = false;
    return this;
  }

  public OrderTable toBeReleased(OrderTableRelatedOrderStatusCheckService domainService) {
    if (domainService.hasInCompletedOrders(this)) {
      throw new IllegalStateException(INCOMPLETED_ORDER_EXIST);
    }
    this.empty = true;
    return this;
  }

  public OrderTable changeNumberOfGuests(int number) {
    if (empty) {
      throw new IllegalStateException(EMPTY_TABLE_CAN_NOT_CHANGE_NUMBER_OF_GUESTS);
    }
    this.numberOfGuests = new NumberOfGuests(number);
    return this;
  }

  public boolean isEmpty() {
    return empty;
  }

  public NumberOfGuests getNumberOfGuests() {
    return numberOfGuests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderTable that = (OrderTable) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
