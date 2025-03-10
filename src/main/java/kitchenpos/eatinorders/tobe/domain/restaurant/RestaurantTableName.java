package kitchenpos.eatinorders.tobe.domain.restaurant;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RestaurantTableName {

    public static final String ERROR_MESSAGE_EMPTY = "가게 테이블 이름이 공백입니다.";

    private String value;

    public RestaurantTableName(final String value) {
        validate(value);

        this.value = value;
    }

    protected RestaurantTableName() {

    }

    private static void validate(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }
    }

    public String getValue() {
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
        RestaurantTableName that = (RestaurantTableName) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
