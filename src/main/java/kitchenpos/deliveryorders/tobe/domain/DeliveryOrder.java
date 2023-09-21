package kitchenpos.deliveryorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "delivery_order")
@Entity
public class DeliveryOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

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

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
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

    private static Map<UUID, Menu> createMenuMap(final List<Menu> menus) {
        return menus.stream()
            .map(menu -> {
                validateDisplayedMenu(menu);
                return menu;
            })
            .collect(Collectors.toMap(
                Menu::getId,
                menu -> menu
            ));
    }

    private static void validateDisplayedMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
    }

    public BigDecimal getSumOfOrderLineItemPrice(List<Menu> menus) {
        return deliveryOrderLineItems.getSumOfOrderLineItemPrice(menus);
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.SERVED;
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

    public void complete() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
