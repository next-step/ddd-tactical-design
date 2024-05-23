package kitchenpos.order.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.common.domain.NonEmptyName;

@Table(name = "order_table")
@Entity
public class OrderTable {
    public  static final String INVALID_ORDER_TABLE_STATUS_ERROR = "사용 중인 주문 테이블의 손님 수만 변경 가능합니다.";

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private NonEmptyName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this(UUID.randomUUID(), name, numberOfGuests, occupied);
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = new NonEmptyName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getValue();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public OrderTable used(boolean use) {
        this.occupied = use;

        if (!use) {
            numberOfGuests = new NumberOfGuests(0);
        }

        return this;
    }

    public OrderTable changedNumberOfGuests(int numberOfGuests) {
        if (!occupied) {
            throw new IllegalStateException(INVALID_ORDER_TABLE_STATUS_ERROR);
        }

        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        return this;
    }
}
