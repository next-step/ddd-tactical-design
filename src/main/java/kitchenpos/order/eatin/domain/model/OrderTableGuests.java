package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.order.eatin.domain.exception.OrderTableGuestsException;

@Embeddable
public record OrderTableGuests(
    @Column(name = "number_of_guests", nullable = false)
    int guests
) {

    public static OrderTableGuests of(int guests) {
        if (guests < 0) {
            throw new OrderTableGuestsException();
        }
        return new OrderTableGuests(guests);
    }

    public int get() {
        return guests;
    }
}

