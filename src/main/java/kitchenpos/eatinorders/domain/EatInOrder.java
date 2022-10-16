package kitchenpos.eatinorders.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    protected EatInOrder() {
    }

    public EatInOrder(
        List<EatInOrderLineItem> eatInOrderLineItems,
        EatInOrderTable eatInOrderTable
    ) {
        this(UUID.randomUUID(), EatInOrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);
    }

    public EatInOrder(
        UUID id,
        EatInOrderStatus status,
        LocalDateTime eatInOrderDateTime,
        List<EatInOrderLineItem> eatInOrderLineItems,
        EatInOrderTable eatInOrderTable
    ) {
        this.id = id;
        this.status = status;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.eatInOrderTable = eatInOrderTable;
    }

    public UUID getId() {
        return id;
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

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public EatInOrderTable getOrderTable() {
        return eatInOrderTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
