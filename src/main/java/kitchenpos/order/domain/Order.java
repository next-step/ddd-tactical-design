package kitchenpos.order.domain;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.eatinorders.domain.OrderTableClearService;
import kitchenpos.order.eatinorders.domain.OrderTableRepository;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.event.OrderStatusChangeEvent;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class Order {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Transient
    private UUID orderTableId;

    public Order() {
    }

    public Order(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, String deliveryAddress, OrderTable orderTable, UUID orderTableId) {
        validateOrderLineItems(orderLineItems);
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
    }

    public void chageStatus(final OrderStatus status) {
        this.status = status;
    }

    public void validateOrderLineItems(OrderLineItems orderLineItems) {
        if (this.type != OrderType.EAT_IN) {
            orderLineItems.getOrderLineItems().stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(quantity -> {
                        if (quantity < 0) {
                            throw new IllegalArgumentException();
                        }
                    });
        }
    }

    public Order create(OrderLineItemsService orderLineItemsService, OrderTableRepository orderTableRepository) {
        OrderLineItems orderLineItems = orderLineItemsService.getOrderLineItems(getOrderLineItems().getOrderLineItems());

        if (getType() == OrderType.EAT_IN) {
            return OrderCreateFactory.eatInOrder(orderLineItems, getOrderTable(orderTableRepository));

        } else if (getType() == OrderType.TAKEOUT) {
            return OrderCreateFactory.takeOutOrder(orderLineItems);

        } else if (getType() == OrderType.DELIVERY) {
            return OrderCreateFactory.deliveryOrder(orderLineItems, getDeliveryAddress());
        }

        throw new IllegalArgumentException("주문 타입이 올바르지 않습니다.");

    }

    public Order accept(ApplicationEventPublisher publisher, KitchenridersClient kitchenridersClient) {
        if (getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        BigDecimal sum = getOrderLineItems().sum();
        if (getType() == OrderType.DELIVERY) {
            kitchenridersClient.requestDelivery(getId(), sum, getDeliveryAddress());
        }
        publisher.publishEvent(new OrderStatusChangeEvent(getId(), OrderStatus.ACCEPTED));
        return this;
    }

    public Order serve(ApplicationEventPublisher publisher) {
        if (getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        publisher.publishEvent(new OrderStatusChangeEvent(getId(), OrderStatus.SERVED));
        return this;
    }

    public Order complete(ApplicationEventPublisher publisher, OrderTableClearService orderTableClearService) {
        if (getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        if (getType() == OrderType.EAT_IN) {
            orderTableClearService.clear(this);
        } else if (getType() == OrderType.DELIVERY) {
            if (status != OrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }

        publisher.publishEvent(new OrderStatusChangeEvent(getId(), OrderStatus.COMPLETED));
        return this;
    }

    public Order startDelivery(ApplicationEventPublisher publisher) {
        if (getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        publisher.publishEvent(new OrderStatusChangeEvent(getId(), OrderStatus.DELIVERING));
        return this;
    }

    public Order completeDelivery(ApplicationEventPublisher publisher) {
        if (getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        publisher.publishEvent(new OrderStatusChangeEvent(getId(), OrderStatus.DELIVERED));
        return this;
    }


    private OrderTable getOrderTable(OrderTableRepository orderRepository) {
        final OrderTable orderTable = orderRepository.findById(getOrderTableId())
                .orElseThrow(NotFoundOrderTableException::new);

        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        return orderTable;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
