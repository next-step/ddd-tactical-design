package kitchenpos.common.domain;

import java.util.Arrays;
import java.util.Objects;

public class Value<T extends Value<T>> {
    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.stream(getClass().getDeclaredFields())
                        .map(field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(this);
                            } catch (final IllegalAccessException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }).toArray()
        );
    }

    @Override
    public boolean equals(final Object obj) {
        return Arrays.stream(getClass().getDeclaredFields())
                .allMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return Objects.equals(field.get(this), field.get(obj));
                    } catch (final IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
    }
}
