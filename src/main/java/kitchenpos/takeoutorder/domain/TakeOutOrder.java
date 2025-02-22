package kitchenpos.takeoutorder.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class TakeOutOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private TakeOutOrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private TakeOutOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TakeOutOrderLineItem> takeOutOrderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;


    public TakeOutOrder() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public TakeOutOrderType getType() {
        return type;
    }

    public void setType(final TakeOutOrderType type) {
        this.type = type;
    }

    public TakeOutOrderStatus getStatus() {
        return status;
    }

    public void setStatus(final TakeOutOrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<TakeOutOrderLineItem> getOrderLineItems() {
        return takeOutOrderLineItems;
    }

    public void setOrderLineItems(final List<TakeOutOrderLineItem> takeOutOrderLineItems) {
        this.takeOutOrderLineItems = takeOutOrderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
