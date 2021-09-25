package kitchenpos.eatinordertables.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuests() {}

    public NumberOfGuests(final int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("방문한 손님 수는 음수가 될 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
