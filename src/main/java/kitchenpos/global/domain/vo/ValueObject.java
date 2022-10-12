package kitchenpos.global.domain.vo;

import java.util.Arrays;
import java.util.Objects;

public abstract class ValueObject {

    @Override
    public boolean equals(Object obj) {
        return Arrays.stream(getClass().getDeclaredFields())
            .allMatch(it -> {
                it.setAccessible(true);
                try {
                    return Objects.equals(it.get(this), it.get(obj));
                } catch (final Exception ignore) {
                    return false;
                }
            });
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            Arrays.stream(getClass().getDeclaredFields())
                .map(it -> {
                    it.setAccessible(true);
                    try {
                        return it.get(this);
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                }).toArray()
        );
    }
}
