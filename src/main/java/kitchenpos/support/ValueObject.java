package kitchenpos.support;

import java.util.Arrays;

public abstract class ValueObject {
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValueObject)) {
            return false;
        }

        return Arrays.stream(getClass().getDeclaredFields())
                .allMatch(field -> {
                    field.setAccessible(true);
                    try {
                        Object thisValue = field.get(this);
                        Object thatValue = field.get(o);
                        return thisValue == null ? thatValue == null : thisValue.equals(thatValue);
                    } catch (IllegalAccessException ignored) {
                        return false;
                    }
                });
    }


    @Override
    public final int hashCode() {
        return Arrays.stream(getClass().getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(this);
                        return value == null ? 0 : value.hashCode();
                    } catch (IllegalAccessException ignored) {
                        return 0;
                    }
                })
                .reduce(0, (a, b) -> a + b);
    }
}
