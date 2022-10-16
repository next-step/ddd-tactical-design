package kitchenpos.eatinorders.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "eat_in_order_date_time", nullable = false)
    private LocalDateTime eatInOrderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_eat_in_order_line_item_to_eat_in_order")
    )
    private List<EatInOrderLineItem> eatInOrderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "eat_in_order_table_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_eat_in_order_to_eat_in_order_table")
    )
    private EatInOrderTable eatInOrderTable;

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

    public EatInOrderStatus getStatus() {
        return status;
    }

    public void setStatus(final EatInOrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getEatInOrderDateTime() {
        return eatInOrderDateTime;
    }

    public void setEatInOrderDateTime(final LocalDateTime eatInOrderDateTime) {
        this.eatInOrderDateTime = eatInOrderDateTime;
    }

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public void setOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public EatInOrderTable getOrderTable() {
        return eatInOrderTable;
    }

    public void setOrderTable(final EatInOrderTable eatInOrderTable) {
        this.eatInOrderTable = eatInOrderTable;
    }
}
