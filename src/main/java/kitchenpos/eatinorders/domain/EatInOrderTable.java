package kitchenpos.eatinorders.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NOT_OCCUPIED_GUESTS;

@Table(name = "order_table")
@Entity
public class EatInOrderTable { // 기존 OrderTable 삭제 후 OrderTable 로 변경 예정
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public EatInOrderTable() {
    }

    private EatInOrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static EatInOrderTable create(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        return new EatInOrderTable(id, name, numberOfGuests, occupied);
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests = NumberOfGuests.ZERO;
        this.occupied = false;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        if (isEmptyTable()) {
            throw new IllegalStateException(NOT_OCCUPIED_GUESTS);
        }
        this.numberOfGuests = numberOfGuests;
    }

    private boolean isEmptyTable() {
        return !occupied;
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getName();
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getNumberOfGuestsValue() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isEmpty() {
        return !occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderTable that = (EatInOrderTable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
