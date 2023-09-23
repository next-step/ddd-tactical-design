package kitchenpos.eatinorders.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public OrderTable(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", name));
        }
        this.name = name;
        this.numberOfGuests = NumberOfGuests.ZERO;
        this.occupied = false;
    }

    public void use() {
        occupied = true;
    }

    public void clean() {
        numberOfGuests = NumberOfGuests.ZERO;
        occupied = false;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (isNotInUse()) {
            throw new IllegalStateException(String.format("사용중이어야 손님 수를 변경할 수 있습니다. 현재 값: %s", occupied));
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean isInUse() {
        return occupied;
    }

    public boolean isNotInUse() {
        return !isInUse();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests.intValue();
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }
}
