package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    private int number;

    public NumberOfGuests(int number) {
        validateNumber(number);
        this.number = number;
    }

    public NumberOfGuests() {

    }

    public int getNumber() {
        return number;
    }

    private void validateNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
    }
}
