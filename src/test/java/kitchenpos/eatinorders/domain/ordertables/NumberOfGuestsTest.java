package kitchenpos.eatinorders.domain.ordertables;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfGuestsTest {

    @DisplayName("손님 수를 생성할 수 있다.")
    @Test
    void create() {
        final NumberOfGuests numberOfGuests = new NumberOfGuests(1);
        assertAll(
                () -> assertNotNull(numberOfGuests),
                () -> assertEquals(1, numberOfGuests.getValue())
        );
    }

    @DisplayName("손님 수가 0보다 작으면 생성할 수 없다.")
    @Test
    void createWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new NumberOfGuests(-1));
    }
}
