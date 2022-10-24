package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class Order {
    private static final String NOT_WAITING_MESSAGE = "대기 상태에만 수락 상태로 변경할 수 있습니다.";
    private static final String NOT_ACCEPT_MESSAGE = "수락 상태에만 서빙 완료 상태로 변경할 수 있습니다.";
    private static final String NOT_SERVED_MESSAGE = "서빙 완료 상태에서만 완료 상태로 변경할 수 있습니다.";
    private static final String CAN_NOT_MINUS_QUANTITY_EXCEPT_EAT_IN = "매장 주문이 아니라면 주문 수량은 0보다 크거나 같아야 한다.";

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;
    private OrderType orderType;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;
    private OrderStatus orderStatus;
    private OrderTable orderTable;
    private UUID orderTableId;
    private String deliveryAddress;


    public static Order createDeliveryOrder(final OrderType orderType, final List<OrderLineItem> orderLineItems,
                                            final OrderStatus orderStatus, final OrderTable orderTable, final String address) {
        return new Order(orderType, orderLineItems, orderStatus, orderTable, address);
    }

    private Order(final OrderType orderType, final List<OrderLineItem> orderLineItems, final OrderStatus orderStatus,
                  final OrderTable orderTable, final String address) {
        if (orderType != OrderType.EAT_IN && orderLineItems.stream().anyMatch(o -> o.getQuantity() < 0)) {
            throw new IllegalArgumentException(CAN_NOT_MINUS_QUANTITY_EXCEPT_EAT_IN);
        }
        this.orderType = orderType;
        this.orderLineItems = orderLineItems;
        this.orderStatus = orderStatus;
        this.orderTable = orderTable;
    }

    public Order(final OrderType orderType, final List<OrderLineItem> orderLineItems, final OrderStatus orderStatus, final OrderTable orderTable) {
        if (orderType != OrderType.EAT_IN && orderLineItems.stream().anyMatch(o -> o.getQuantity() < 0)) {
            throw new IllegalArgumentException(CAN_NOT_MINUS_QUANTITY_EXCEPT_EAT_IN);
        }
        this.orderType = orderType;
        this.orderLineItems = orderLineItems;
        this.orderStatus = orderStatus;
        this.orderTable = orderTable;
    }

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void accept(KitchenridersClient client) {
        if (orderStatus != OrderStatus.WAITING) {
            throw new IllegalArgumentException(NOT_WAITING_MESSAGE);
        }
        if (orderType == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final OrderLineItem orderLineItem : this.getOrderLineItems()) {
                sum = orderLineItem.getMenu()
                        .getPrice()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
            }
            client.requestDelivery(this.id, sum, this.getDeliveryAddress());
        }
        orderStatus = OrderStatus.ACCEPTED;
    }

    public void accept() {
        if (orderStatus != OrderStatus.WAITING) {
            throw new IllegalArgumentException(NOT_WAITING_MESSAGE);
        }
        orderStatus = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (orderStatus != OrderStatus.ACCEPTED) {
            throw new IllegalArgumentException(NOT_ACCEPT_MESSAGE);
        }
        orderStatus = OrderStatus.SERVED;
    }

    public void complete() {
        if (orderStatus != OrderStatus.SERVED) {
            throw new IllegalArgumentException(NOT_ACCEPT_MESSAGE);
        }
        orderStatus = OrderStatus.COMPLETED;
        if (orderType == OrderType.EAT_IN) {
            orderTable.makeEmpty();
        }
    }

    public void startDelivery() {
        if (orderType != OrderType.DELIVERY) {
            throw new IllegalArgumentException();
        }
        if (orderStatus != OrderStatus.SERVED) {
            throw new IllegalArgumentException();
        }
        orderStatus = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (orderStatus != OrderStatus.DELIVERING) {
            throw new IllegalArgumentException();
        }
        orderStatus = OrderStatus.DELIVERED;
    }
}
