package kitchenpos.eatinorders.tobe.domain.restaurant;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RestaurantTableOccupied {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 입니다.";

    private boolean value;

    public RestaurantTableOccupied(final Boolean value) {
        validate(value);
        this.value = value;
    }

    protected RestaurantTableOccupied() {

    }

    private static void validate(final Boolean value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_VALUE_NULL);
        }
    }

    public boolean getValue() {
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
        RestaurantTableOccupied that = (RestaurantTableOccupied) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
