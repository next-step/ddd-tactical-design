package kitchenpos.order.eatin.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kitchenpos.order.eatin.domain.model.OrderTableGuests;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableName;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "order_table")
@Entity
@DynamicUpdate
public class OrderTable {

    @EmbeddedId
    private OrderTableId orderTableId;

    @Embedded
    private OrderTableName name;

    @Embedded
    private OrderTableGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {}

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public OrderTableName getName() {
        return name;
    }

    public OrderTableGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public OrderTable(OrderTableId orderTableId, OrderTableName name, OrderTableGuests numberOfGuests, boolean occupied) {
        this.orderTableId = orderTableId;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void updateOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void updateNumberOfGuests(OrderTableGuests guests) {
        this.numberOfGuests = guests;
    }

    public void clear() {
        updateNumberOfGuests(OrderTableGuests.of(0));
        updateOccupied(false);
    }

    public void validateOccupied(){
        if (!this.isOccupied()) {
            throw new IllegalStateException();
        };
    }
}
