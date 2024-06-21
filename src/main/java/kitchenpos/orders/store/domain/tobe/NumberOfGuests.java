package kitchenpos.orders.store.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NumberOfGuests {

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuests(){
    }

    public NumberOfGuests(int numberOfGuests) {
        if(numberOfGuests < 0){
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numberOfGuests);
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
