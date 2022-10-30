package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException.*;

@Embeddable
public class OrderTableStatus {

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;
    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public static OrderTableStatus ofEmpty() {
        return new OrderTableStatus(0, false);
    }

    protected OrderTableStatus() {
    }

    private OrderTableStatus(int numberOfGuests, boolean occupied) {
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    OrderTableStatus changeNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalOrderTableStatusException(NEGATIVE_NUMBER_OF_GUESTS);
        }
        if (!occupied) {
            throw new IllegalOrderTableStatusException(UNOCCUPIED);
        }
        return new OrderTableStatus(numberOfGuests, true);
    }

    OrderTableStatus occupy() {
        if (occupied) {
            throw new IllegalOrderTableStatusException(ALREADY_OCCUPIED);
        }
        return new OrderTableStatus(0, true);
    }

    OrderTableStatus clear() {
        return new OrderTableStatus(0, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableStatus status = (OrderTableStatus) o;
        return numberOfGuests == status.numberOfGuests && occupied == status.occupied;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests, occupied);
    }
}
