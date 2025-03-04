package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.common.vo.PositiveNumber;

@Entity
@Table(name = "order_table")
public class OrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private PositiveNumber numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public static OrderTable create(String name) {
        return new OrderTable(
                OrderTableId.generate(),
                new OrderTableName(name),
                PositiveNumber.ZERO,
                false
        );
    }

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this(OrderTableId.generate(), new OrderTableName(name), new PositiveNumber(numberOfGuests), occupied);
    }

    public OrderTable(OrderTableId id, OrderTableName name, PositiveNumber numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.occupied = false;
        this.numberOfGuests = PositiveNumber.ZERO;
    }

    public void changeNumberOfGuests(int number) {
        changeNumberOfGuests(new PositiveNumber(number));
    }

    public void changeNumberOfGuests(PositiveNumber number) {
        this.numberOfGuests = number;
    }

    public OrderTableId getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public PositiveNumber getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isNotOccupied() {
        return !occupied;
    }
}
