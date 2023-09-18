package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class ToBeOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ToBeOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<ToBeOrderLineItem> orderLineItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private ToBeOrderTable orderTable;

    @Transient
    private UUID orderTableId;

    protected ToBeOrder() {
    }

    public ToBeOrder(ToBeOrderStatus toBeOrderStatus, LocalDateTime localDateTime, List<ToBeOrderLineItem> orderLineItems){
        this.status = toBeOrderStatus;
        this.orderDateTime = localDateTime;
        this.orderLineItems = orderLineItems;
    }


    public UUID getId() {
        return id;
    }

    public ToBeOrderStatus getStatus() {
        return status;
    }

    public void changeStatus(ToBeOrderStatus status){
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }


    public List<ToBeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }


    public ToBeOrderTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(ToBeOrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

}
