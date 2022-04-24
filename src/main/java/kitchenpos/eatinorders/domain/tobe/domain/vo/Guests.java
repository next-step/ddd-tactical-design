package kitchenpos.eatinorders.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Guests {

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected Guests() {

    }

    public Guests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = numberOfGuests;
    }

    public int getValue() {
        return numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guests guests = (Guests) o;

        return numberOfGuests == guests.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return numberOfGuests;
    }
}
