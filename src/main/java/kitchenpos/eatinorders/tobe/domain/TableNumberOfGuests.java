package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TableNumberOfGuests {

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected TableNumberOfGuests() {
    }

    public TableNumberOfGuests(int numberOfGuests) {
        validtaionNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void validtaionNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void clear() {
        this.numberOfGuests = 0;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        validtaionNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }
}
