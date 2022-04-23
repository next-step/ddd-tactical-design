package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.vo.OrderLineItemReq;
import kitchenpos.eatinorders.tobe.domain.domainservice.MenuDomainService;
import kitchenpos.eatinorders.tobe.domain.domainservice.OrderTableDomainService;

@Entity
@Table(name = "eat_in_order")
public class EatInOrder {

  private static final String CAN_ACCEPT_WHEN_WAITING = "접수 대기 상태인 주문만 접수할 수 있습니다.";
  private static final String CAN_SERVE_WHEN_ACCEPTED = "접수된 주문만 제공할 수 있습니다.";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private EatInOrderStatus orderStatus;

  @Embedded
  private EatInOrderLineItems orderLineItems;

  private Long orderTableId;

  protected EatInOrder() {
  }

  public EatInOrder(List<OrderLineItemReq> orderLineItemReqs, MenuDomainService menuDomainService,
      Long orderTableId, OrderTableDomainService orderTableDomainService) {
    this.orderStatus = EatInOrderStatus.WAITING;
    this.orderLineItems = new EatInOrderLineItems(menuDomainService, orderLineItemReqs);
    validateOrderTableId(orderTableId, orderTableDomainService);
    this.orderTableId = orderTableId;
  }

  private void validateOrderTableId(Long orderTableId, OrderTableDomainService orderTableDomainService) {
    if (orderTableDomainService.isEmptyOrderTable(orderTableId)) {
      throw new IllegalArgumentException();
    }
  }

  public EatInOrder accept() {
    if (orderStatus != EatInOrderStatus.WAITING) {
      throw new IllegalStateException(CAN_ACCEPT_WHEN_WAITING);

    }
    this.orderStatus = EatInOrderStatus.ACCEPTED;
    return this;
  }

  public EatInOrder serve() {
    if (orderStatus != EatInOrderStatus.ACCEPTED) {
      throw new IllegalStateException(CAN_SERVE_WHEN_ACCEPTED);
    }
    this.orderStatus = EatInOrderStatus.SERVED;
    return this;
  }

  public EatInOrderStatus getOrderStatus() {
    return orderStatus;
  }

  public EatInOrder complete(OrderTableDomainService orderTableDomainService) {
    if (orderStatus != EatInOrderStatus.SERVED) {
      throw new IllegalStateException();
    }

    if (!orderTableDomainService.hasInCompletedOrders(orderTableId)) {
      orderTableDomainService.emptyTable(orderTableId);
    }

    this.orderStatus = EatInOrderStatus.COMPLETED;
    return this;
  }
}
