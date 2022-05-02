package kitchenpos.eatinorders.tobe;

import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    private static final int INITIAL_NUMBER = 0;

    private final int numberOfGuests;

    private NumberOfGuests(int numberOfGuests) {
        validateNumber(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void validateNumber(int numberOfGuests) {
        if (numberOfGuests < INITIAL_NUMBER) {
            throw new IllegalArgumentException();
        }
    }

    public static NumberOfGuests init() {
        return new NumberOfGuests(INITIAL_NUMBER);
    }

    public NumberOfGuests changeNumberOfGuests(int changeNumber) {
        return new NumberOfGuests(changeNumber);
    }

    public NumberOfGuests clear() {
        return init();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
