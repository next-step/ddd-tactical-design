package kitchenpos.support.domain;

import java.util.Arrays;
import java.util.Objects;

public abstract class ValueObject {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Arrays.stream(getClass().getDeclaredFields())
                .allMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(this).equals(field.get(obj));
                    } catch (IllegalAccessException ignore) {
                        return false;
                    }
                });
    }

    @Override
    public int hashCode() {
        final Object[] values = Arrays.stream(getClass().getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(this);
                    } catch (IllegalAccessException ignore) {
                        return null;
                    }
                }).toArray();
        return Objects.hash(values);
    }
}
