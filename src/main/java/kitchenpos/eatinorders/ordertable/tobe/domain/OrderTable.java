package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.ordertable.tobe.domain.exception.NoUsedOrderTableException;
import kitchenpos.eatinorders.ordertable.tobe.domain.vo.GuestOfNumbers;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

public class OrderTable {

    private static final boolean EMPTY_TABLE = false;
    private static final boolean USED_TABLE = true;

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private GuestOfNumbers guestOfNumbers;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    private OrderTable(final Name name, final GuestOfNumbers guestOfNumbers, final boolean occupied) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.guestOfNumbers = guestOfNumbers;
        this.occupied = occupied;
    }

    public static OrderTable createEmptyTable(final String name) {
        return new OrderTable(Name.valueOf(name), GuestOfNumbers.ZERO, EMPTY_TABLE);
    }

    public void use() {
        occupied = USED_TABLE;
    }

    void clear() {
        guestOfNumbers = GuestOfNumbers.ZERO;
        occupied = EMPTY_TABLE;
    }

    public void changeGuestOfNumbers(final GuestOfNumbers guestOfNumbers) {
        if (isEmptyTable()) {
            throw new NoUsedOrderTableException();
        }
        this.guestOfNumbers = guestOfNumbers;
    }

    public UUID id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public GuestOfNumbers guestOfNumbers() {
        return guestOfNumbers;
    }

    public boolean isEmptyTable() {
        return occupied == EMPTY_TABLE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
