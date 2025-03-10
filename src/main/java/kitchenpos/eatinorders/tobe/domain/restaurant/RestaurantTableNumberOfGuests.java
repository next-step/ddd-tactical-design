package kitchenpos.eatinorders.tobe.domain.restaurant;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RestaurantTableNumberOfGuests {

    public static final String ERROR_MESSAGE_MINUS = "값이 마이너스 입니다.";
    private static final int MINIMUM_VALUE = 0;
    private int value;

    public RestaurantTableNumberOfGuests(final int value) {
        validate(value);
        this.value = value;
    }

    protected RestaurantTableNumberOfGuests() {

    }

    private static void validate(final int value) {
        if (value < MINIMUM_VALUE) {
            throw new IllegalArgumentException(ERROR_MESSAGE_MINUS);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RestaurantTableNumberOfGuests that = (RestaurantTableNumberOfGuests) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
