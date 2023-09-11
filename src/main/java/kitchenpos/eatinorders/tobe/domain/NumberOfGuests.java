package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class NumberOfGuests {
    private int numberOfGuests;

    public NumberOfGuests(int numberOfGuests){
        checkNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    public void checkNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
