package kitchenpos.deliveryorder.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "orders")
@Entity
public class DeliveryOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryOrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<DeliveryOrderLineItem> deliveryOrderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private DeliveryOrderTable deliveryOrderTable;

    @Transient
    private UUID orderTableId;

    public DeliveryOrder() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public DeliveryOrderType getType() {
        return type;
    }

    public void setType(final DeliveryOrderType type) {
        this.type = type;
    }

    public DeliveryOrderStatus getStatus() {
        return status;
    }

    public void setStatus(final DeliveryOrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<DeliveryOrderLineItem> getOrderLineItems() {
        return deliveryOrderLineItems;
    }

    public void setOrderLineItems(final List<DeliveryOrderLineItem> deliveryOrderLineItems) {
        this.deliveryOrderLineItems = deliveryOrderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryOrderTable getOrderTable() {
        return deliveryOrderTable;
    }

    public void setOrderTable(final DeliveryOrderTable deliveryOrderTable) {
        this.deliveryOrderTable = deliveryOrderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
