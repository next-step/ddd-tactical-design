package kitchenpos.order.eatin.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableName;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {}


    public OrderTableId getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public OrderTable(OrderTableId id, OrderTableName name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }
}
