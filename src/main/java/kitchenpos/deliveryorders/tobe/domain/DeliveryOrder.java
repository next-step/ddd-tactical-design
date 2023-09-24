package kitchenpos.deliveryorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.sharedkernel.Order;
import kitchenpos.sharedkernel.OrderStatus;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "delivery_order")
@Entity
public class DeliveryOrder extends Order {
    @Embedded
    private DeliveryOrderLineItems deliveryOrderLineItems;

    @Embedded
    @Column(name = "delivery_address")
    private DeliveryAddress deliveryAddress;

    protected DeliveryOrder() {
    }

    public DeliveryOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final DeliveryOrderLineItems deliveryOrderLineItems,
        final DeliveryAddress deliveryAddress
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.deliveryOrderLineItems = deliveryOrderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryOrderLineItems getOrderLineItems() {
        return deliveryOrderLineItems;
    }

    public List<DeliveryOrderLineItem> getOrderLineItemList() {
        return deliveryOrderLineItems.getOrderLineItemList();
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getStringDeliveryAddress() {
        return deliveryAddress.getAddress();
    }

    public static DeliveryOrder create(
        final List<Menu> menus,
        final List<DeliveryOrderLineItem> deliveryOrderLineItemList,
        final String deliveryAddress
    ) {
        return new DeliveryOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new DeliveryOrderLineItems(deliveryOrderLineItemList, createMenuMap(menus)),
            new DeliveryAddress(deliveryAddress)
        );
    }

    public BigDecimal getSumOfOrderLineItemPrice(List<Menu> menus) {
        return deliveryOrderLineItems.getSumOfOrderLineItemPrice(menus);
    }

    public void startDelivery() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (status != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.DELIVERED;
    }

    @Override
    public void complete() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
