package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
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
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(String name) {
        this(UUID.randomUUID(), name, 0, false);
    }

    protected OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name 은 비어있을 수 없습니다.");
        }
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests = 0;
        this.occupied = false;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        validateNumberOfGuests(numberOfGuests);
        validateOccupied();
        this.numberOfGuests = numberOfGuests;
    }

    private void validateNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("방문한 손님 수는 0보다 작을수 없습니다.");
        }
    }

    private void validateOccupied() {
        if (!occupied) {
            throw new IllegalStateException("빈 테이블은 손님 수를 변경할 수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
