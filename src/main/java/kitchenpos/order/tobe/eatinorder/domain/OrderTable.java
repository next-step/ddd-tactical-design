package kitchenpos.order.tobe.eatinorder.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity
public class OrderTable {

    public static final int INIT_NUMBER_OF_GUESTS = 0;
    public static final int MIN_NUMBER_OF_GUESTS = 0;

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTable empty(UUID id, String name) {
        return new OrderTable(id, name, INIT_NUMBER_OF_GUESTS, false);
    }

    public void clear() {
        this.numberOfGuests = INIT_NUMBER_OF_GUESTS;
        this.occupied = false;
    }

    public void changeInUseTable() {
        this.occupied = true;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < MIN_NUMBER_OF_GUESTS) {
            throw new IllegalArgumentException(MIN_NUMBER_OF_GUESTS + "이상의 손님 수로만 변경할 수 있습니다. input numberOfGuests = " + numberOfGuests);
        }

        if (!occupied) {
            throw new IllegalStateException("사용중인 테이블의 손님 수만 수정할 수 있습니다.");
        }

        this.numberOfGuests = numberOfGuests;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
