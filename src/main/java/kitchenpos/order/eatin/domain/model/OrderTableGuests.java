package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menu.domain.exception.MenuProductQtyException;

@Embeddable
public record OrderTableGuests(
    @Column(name = "number_of_guests", nullable = false)
    int guests
) {

    public static OrderTableGuests of(int guests) {
        if (guests < 0) {
            throw new MenuProductQtyException();
        }
        return new OrderTableGuests(guests);
    }

    public long get() {
        return guests;
    }
}

