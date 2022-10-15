package kitchenpos.eatinorders.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_eat_in_order_line_item_to_eat_in_order")
    )
    private List<EatInOrderLineItem> eatInOrderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(
        name = "eat_in_order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_eat_in_order_to_eat_in_order_table")
    )
    private EatInOrderTable eatInOrderTable;

    @Transient
    private UUID orderTableId;

    public EatInOrder() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public OrderType getType() {
        return OrderType.EAT_IN;
    }

    public void setType(final OrderType type) {

    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return eatInOrderLineItems;
    }

    public void setOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public EatInOrderTable getOrderTable() {
        return eatInOrderTable;
    }

    public void setOrderTable(final EatInOrderTable eatInOrderTable) {
        this.eatInOrderTable = eatInOrderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
